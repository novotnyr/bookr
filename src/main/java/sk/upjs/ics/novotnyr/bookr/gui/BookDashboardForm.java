package sk.upjs.ics.novotnyr.bookr.gui;

import net.miginfocom.swing.MigLayout;
import sk.upjs.ics.novotnyr.bookr.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class BookDashboardForm extends JFrame {
    private final BookDetailPanel panBookDetail;
    private BookDao bookDao = BeanFactory.INSTANCE.bookDao();
    private PublisherDao publisherDao = BeanFactory.INSTANCE.publisherDao();
    private BookDashboardListCellRenderer bookDashboardListCellRenderer = new BookDashboardListCellRenderer();
    private JButton btnSearch = new JButton("Search");
    private JList lstBooks = new JList();
    private JScrollPane scrollPaneLstBooks = new JScrollPane(lstBooks);
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem menuBookAdd = new JMenuItem("Add...");
    private JMenu menuBooks = new JMenu("Books...");
    private JMenuItem menuPublisherAdd = new JMenuItem("Add...");
    private JMenuItem menuPublishersList = new JMenuItem("Show All...");
    private JMenu mnuPublishers = new JMenu();
    private JTextField txtSearchQuery = new JTextField();
    private JLabel lblCmbPublisher = new JLabel("Publisher: ");
    private JComboBox cmbPublisher = new JComboBox();
    private ListCellRenderer publisherListCellRenderer = new PublisherListCellRenderer();
    private JPanel panSearch = createSearchPanel();

    public BookDashboardForm() {
        setTitle("BookR");

        setLayout(new MigLayout("wrap", "[fill, grow]"));

        add(panSearch);

        add(scrollPaneLstBooks, "dock center");
        lstBooks.setCellRenderer(this.bookDashboardListCellRenderer);
        lstBooks.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        lstBooks.setVisibleRowCount(-1);
        lstBooks.setPrototypeCellValue(getPrototypeBookValue());
        lstBooks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                lstBooksSelectionChanged(e);
            }
        });
        lstBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    lstBooksMouseDoubleClicked(e);
                }
            }
        });
        lstBooks.setComponentPopupMenu(createLstBooksPopupMenu());
        refreshBookData();

        panBookDetail = new BookDetailPanel();
        add(panBookDetail.getPanel());

        initMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setPreferredSize(new Dimension(800, 600));

        pack();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new MigLayout("insets 0", "[fill, grow][][][]"));
        searchPanel.add(txtSearchQuery);
        searchPanel.add(btnSearch);
        searchPanel.add(lblCmbPublisher);
        searchPanel.add(cmbPublisher);
        cmbPublisher.setModel(getPublisherComboBoxModel());
        cmbPublisher.setRenderer(publisherListCellRenderer);
        cmbPublisher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbPublisherActionPerformed(e);
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSearchActionPerformed(e);
            }
        });

        txtSearchQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearchQueryActionPerfomed(e);
            }
        });

        return searchPanel;
    }

    public void refreshBookData() {
        lstBooks.setListData(bookDao.list().toArray());
    }

    private void initMenu() {
        menuBooks.setText("Books");
        menuBar.add(menuBooks);

        menuBookAdd.setText("Add...");
        menuBookAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBookAddActionPerformed(evt);
            }
        });
        menuBooks.add(menuBookAdd);

        mnuPublishers.setText("Publishers");
        menuBar.add(mnuPublishers);

        menuPublisherAdd.setText("Add...");
        menuPublisherAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPublisherAddActionPerformed(evt);
            }
        });
        mnuPublishers.add(menuPublisherAdd);

        menuPublishersList.setText("Show All...");
        menuPublishersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPublishersListActionPerformed(evt);
            }
        });
        mnuPublishers.add(menuPublishersList);

        setJMenuBar(menuBar);
    }

    private void menuPublisherAddActionPerformed(java.awt.event.ActionEvent evt) {
        PublisherEditForm editForm = new PublisherEditForm(this);
        editForm.setVisible(true);
    }

    private void menuPublishersListActionPerformed(java.awt.event.ActionEvent evt) {
        PublisherDashboardForm dashboardForm = new PublisherDashboardForm(this);
        dashboardForm.setVisible(true);
    }

    private void menuBookAddActionPerformed(java.awt.event.ActionEvent evt) {
        BookEditForm editForm = new BookEditForm(this);
        editForm.setLocationByPlatform(true);
        editForm.setVisible(true);

        refreshBookData();
    }

    private void btnSearchActionPerformed(ActionEvent e) {
        List<Book> books = bookDao.searchByTitle(txtSearchQuery.getText());
        lstBooks.setListData(books.toArray());
    }


    private void lstBooksSelectionChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        Book selectedBook = (Book) lstBooks.getSelectedValue();
        if (selectedBook != null) {
            panBookDetail.setBook(selectedBook);
        }
    }

    private void txtSearchQueryActionPerfomed(ActionEvent e) {
        btnSearchActionPerformed(e);
    }

    private void lstBooksMouseDoubleClicked(MouseEvent e) {
        Book selectedBook = (Book) lstBooks.getSelectedValue();
        if (selectedBook != null) {
            BookEditForm bookEditForm = new BookEditForm(this, selectedBook);
            bookEditForm.setVisible(true);
            refreshBookData();
        }
    }

    protected JPopupMenu createLstBooksPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupLstBooksDeleteActionPerformed(e);
            }
        });
        popupMenu.add(new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupLstBooksEditActionPerformed(e);
            }
        });
        popupMenu.add(new AbstractAction("Comments") {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupLstBooksCommentActionPerformed(e);
            }
        });
        return popupMenu;
    }


    private void popupLstBooksDeleteActionPerformed(ActionEvent e) {
        Book selectedBook = (Book) lstBooks.getSelectedValue();
        if (selectedBook != null) {
            int result = DialogUtils.yesNoDialog(this,
                    "The book will be deleted! "
                            + selectedBook.getTitle());
            if (result == JOptionPane.YES_OPTION) {
                bookDao.delete(selectedBook);
            }
        }
        refreshBookData();
    }

    private void popupLstBooksEditActionPerformed(ActionEvent e) {
        lstBooksMouseDoubleClicked(DialogUtils.NO_MOUSE_EVENT);
    }

    private void popupLstBooksCommentActionPerformed(ActionEvent e) {
        Book selectedBook = (Book) lstBooks.getSelectedValue();
        if (selectedBook != null) {
            new BookDetailForm(selectedBook);
        }
        refreshBookData();
    }

    private void cmbPublisherActionPerformed(ActionEvent e) {
        Publisher publisher = (Publisher) cmbPublisher.getSelectedItem();
        if (Publisher.isNullPublisher(publisher)) {
            refreshBookData();
        } else {
            lstBooks.setListData(bookDao.findByPublisher(publisher).toArray());
        }
    }

    private Book getPrototypeBookValue() {
        Book book = new Book();
        book.setTitle("A very long book name");

        return book;
    }

    private ComboBoxModel getPublisherComboBoxModel() {
        List<Publisher> publishers = publisherDao.list();
        publishers.add(0, Publisher.getNullPublisher());
        return new DefaultComboBoxModel(publishers.toArray());
    }

}
