package sk.upjs.ics.novotnyr.bookr;

import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.novotnyr.bookr.dao.BookCoverRowMapper;
import sk.upjs.ics.novotnyr.bookr.gui.BookDashboardListCellRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HsqlImageCoverService implements ImageCoverService {
    private static final int DEFAULT_THUMBNAIL_HEIGHT = 80;
    private static final int DEFAULT_THUMBNAIL_WIDTH = 50;
    private static Logger logger = LoggerFactory.getLogger(HsqlImageCoverService.class);
    private JdbcTemplate jdbcTemplate;
    private Map<Long, ImageIcon> imageCoverCache = new LRUMap(20);
    private BookCoverRowMapper bookCoverRowMapper = new BookCoverRowMapper();

    public HsqlImageCoverService() {
    }

    public HsqlImageCoverService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ImageIcon getImageCover(Book book) {
        if (imageCoverCache.containsKey(book.getId())) {
            //logger.debug("Loading image cover for {} from cache", book.getId());
            return imageCoverCache.get(book.getId());
        }

        List<ImageIcon> imageIcons = jdbcTemplate.query("SELECT * FROM book_cover WHERE book_id = ?", bookCoverRowMapper, book.getId());
        ImageIcon imageIcon;
        if (imageIcons.isEmpty()) {
            imageIcon = getDefaultIcon(book);
        } else {
            imageIcon = imageIcons.get(0);
            imageIcon = resize(imageIcon, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT);
        }
        cache(book, imageIcon);
        return imageIcon;
    }


    private ImageIcon getDefaultIcon(Book book) {
        URL bookIconUrl = BookDashboardListCellRenderer.class.getResource("book-icon.png");
        return new ImageIcon(bookIconUrl);
    }

    @Override
    public void save(Book book, ImageIcon icon) {
        try {
            byte[] imageBytes = toByteArray(icon);
            jdbcTemplate.update("INSERT INTO book_cover VALUES(?, ?)", book.getId(), imageBytes);
        } catch (IOException e) {
            throw new BookCoverException("Cannot save book cover image", e);
        }
    }

    public ImageIcon resize(ImageIcon icon, int width, int height) {
        Image image = icon.getImage().getScaledInstance(DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    protected BufferedImage toBufferedImage(ImageIcon imageIcon) {
        return new BufferedImage(
                imageIcon.getIconWidth(),
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB
        );
    }

    /**
     * @see http://www.coderanch.com/t/384153/java/java/writing-ImageIcon-object-ro-file
     */
    protected byte[] toByteArray(ImageIcon imageIcon) throws IOException {
        Image image = imageIcon.getImage();
        RenderedImage renderedImage = null;
        if (image instanceof RenderedImage) {
            renderedImage = (RenderedImage) image;
        } else {
            BufferedImage bufferedImage = new BufferedImage(
                    imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            renderedImage = bufferedImage;
        }

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(renderedImage, "JPEG", byteArrayStream);
        return byteArrayStream.toByteArray();
    }

    private void cache(Book book, ImageIcon imageIcon) {
        imageCoverCache.put(book.getId(), imageIcon);
    }
}
