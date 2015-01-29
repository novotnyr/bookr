package sk.upjs.ics.novotnyr.bookr.gui;

import net.miginfocom.swing.MigLayout;
import sk.upjs.ics.novotnyr.bookr.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


public class BookEditForm extends JDialog {
    private BookDao bookDao = BeanFactory.INSTANCE.bookDao();

    private ImageCoverService imageCoverService = BeanFactory.INSTANCE.imageCoverService();

    private PublisherDao publisherDao = BeanFactory.INSTANCE.publisherDao();

    private JLabel lblTitle = new JLabel("Title:");

    private JLabel lblPublisher = new JLabel("Publisher:");

    private JLabel lblYear = new JLabel("Year:");

    private JTextField txtTitle = new JTextField();

    private JComboBox cmbPublisher = new JComboBox();

    private ListCellRenderer publisherListCellRenderer = new PublisherListCellRenderer();

    private JButton btnPublisherAdd = new JButton("+");

    private JFormattedTextField txtYear = new JFormattedTextField(createYearFormatter());

    private JLabel lblCover = new JLabel("Cover:");

    private JLabel imgCover = new JLabel("Double-click to load cover");

    private JButton btnOk = new JButton("OK");

    private JButton btnCancel = new JButton("Cancel");

    private Book book;

    public BookEditForm(Frame owner) {
        this(owner, new Book());
    }

    public BookEditForm(Frame owner, Book book) {
        super(owner, "Publishers", /* modal*/ true);

        this.book = book;

        setLayout(new MigLayout("wrap 3, width 200:500:", "[][grow, fill][]", "[][][][][nogrid]"));
        
        /* -- Titles - */
        add(lblTitle);

        add(txtTitle, "span 2");
        txtTitle.setText(book.getTitle());

        /* -- Publisher - */
        add(lblPublisher);

        refreshCmbPublisherModel();
        cmbPublisher.setRenderer(publisherListCellRenderer);
        if (book.getPublisher() != null) {
            cmbPublisher.setSelectedItem(book.getPublisher());
        }

        add(cmbPublisher);

        add(btnPublisherAdd);
        btnPublisherAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPublisherAddActionPerformed(e);
            }
        });

        /* -- Year - */
        add(lblYear);

        add(txtYear, "span 2");
        txtYear.setText(parseYear(book.getYear()));
        
        
        /* -- Book Cover - */
        add(lblCover);

        imgCover.setBackground(Color.WHITE);
        imgCover.setOpaque(true);
        imgCover.setHorizontalAlignment(SwingConstants.CENTER);
        imgCover.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imgCoverMouseClicked(e);
            }
        });
        ImageIcon coverImageIcon = imageCoverService.getImageCover(book);
        if (coverImageIcon != null) {
            imgCover.setIcon(coverImageIcon);
            imgCover.setText("");
        }
        add(imgCover, "hmin 300, span 2");

        add(btnOk, "tag ok");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOkActionPerformed(e);
            }
        });

        add(btnCancel, "tag cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelActionPerformed(e);
            }
        });

        DialogUtils.addEscapeListener(this);

        pack();
        setLocationRelativeTo(null);
    }

    private JFormattedTextField.AbstractFormatter createYearFormatter() {
        try {
            return new MaskFormatter("####");
        } catch (ParseException e) {
            // should not happen
            throw new IllegalStateException("Illegal syntax for year formatter");
        }
    }

    private ComboBoxModel getPublisherComboBoxModel() {
        List<Publisher> publishers = publisherDao.list();
        return new DefaultComboBoxModel(publishers.toArray());
    }

    private void btnOkActionPerformed(ActionEvent e) {
        book.setTitle(txtTitle.getText());
        book.setPublisher((Publisher) cmbPublisher.getSelectedItem());
        book.setYear(Integer.valueOf(txtYear.getText()));

        bookDao.saveOrUpdate(book);
        imageCoverService.save(book, (ImageIcon) imgCover.getIcon());

        setVisible(false);
    }

    private void imgCoverMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JFileChooser fileChooser = new JFileChooser();
            int dialogResult = fileChooser.showOpenDialog(this);
            if (dialogResult == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadCover(selectedFile);
            }
        }
    }

    private void btnCancelActionPerformed(ActionEvent e) {
        setVisible(false);
    }


    private void btnPublisherAddActionPerformed(ActionEvent e) {
        PublisherEditForm publisherEditForm = new PublisherEditForm((Frame) getOwner());
        publisherEditForm.setVisible(true);
        refreshCmbPublisherModel();
    }

    private void loadCover(File bookCoverFile) {
        try {
            BufferedImage image = ImageIO.read(bookCoverFile);
            imgCover.setIcon(new ImageIcon(image));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to load book cover", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshCmbPublisherModel() {
        cmbPublisher.setModel(getPublisherComboBoxModel());
    }

    private String parseYear(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year <= 0) {
            year = currentYear;
        }
        return Integer.toString(year);
    }


}
