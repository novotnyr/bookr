package sk.upjs.ics.novotnyr.bookr.gui;

import sk.upjs.ics.novotnyr.bookr.BeanFactory;
import sk.upjs.ics.novotnyr.bookr.Book;
import sk.upjs.ics.novotnyr.bookr.ImageCoverService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BookDashboardListCellRenderer implements ListCellRenderer<Book> {
    private ImageCoverService imageCoverService = BeanFactory.INSTANCE.imageCoverService();

    private DefaultListCellRenderer delegate = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Book> list, Book book, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = delegate.getListCellRendererComponent(list, book, index, isSelected, cellHasFocus);
        if (component instanceof JLabel) {
            // DefaultListCellRenderer vracia vždy JLabel s textom, viď dokumentácia
            JLabel label = (JLabel) component;
            label.setText(book.getTitle());
            label.setIcon(getIcon(book));
            return label;
        } else {
            throw new ClassCastException("Illegal JList component type. "
                    + "Expected JLabel, but found "
                    + component.getClass());
        }
    }

    private Icon getIcon(Book book) {
        ImageIcon imageCover = imageCoverService.getImageCover(book);
        if (imageCover == null) {
            URL bookIconUrl = BookDashboardListCellRenderer.class.getResource("book-icon.png");
            return new ImageIcon(bookIconUrl);
        }
        return imageCover;
    }

}
