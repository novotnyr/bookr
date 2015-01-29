package sk.upjs.ics.novotnyr.bookr.gui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PublisherDashboardForm extends JDialog {

    private JTextField txtSearchQuery = new JTextField("Search...");

    private JButton btnSearch = new JButton("Search");

    private JButton btnResetSearch = new JButton("Clear");

    private JTable tabPublishers = new JTable();

    private JScrollPane tabPublishersScrollPane = new JScrollPane(tabPublishers);

    private PublisherTableModel publisherTableModel = new PublisherTableModel();

    private JButton btnAddPublisher = new JButton("Add publisher...");

    private TableRowSorter publisherTableRowSorter;

    public PublisherDashboardForm(Frame owner) {
        super(owner, "Publisher Overview", /* modal */ true);

        setLayout(new MigLayout("wrap 3", "[grow, fill][][]", "[][][nogrid]"));

        add(txtSearchQuery);

        add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSearchActionPerformed(e);
            }
        });

        add(btnResetSearch);
        btnResetSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnResetSearchActionPerformed(e);
            }
        });

        publisherTableModel.refresh();
        tabPublishers.setModel(publisherTableModel);
        tabPublishers.setRowSorter(createTableRowSorter());
        tabPublishers.setComponentPopupMenu(createPopupMenu());

        add(tabPublishersScrollPane, "dock center, span 3, wrap");

        add(btnAddPublisher, "skip 1");
        btnAddPublisher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddPublisherActionPerformed(e);
            }
        });

        pack();

        setPreferredSize(new Dimension(640, 480));
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new WindowsLookAndFeel());

        PublisherDashboardForm publisherDashboardForm = new PublisherDashboardForm(null);
        publisherDashboardForm.setDefaultCloseOperation(PublisherDashboardForm.DISPOSE_ON_CLOSE);
        publisherDashboardForm.setVisible(true);

    }

    private TableRowSorter createTableRowSorter() {
        this.publisherTableRowSorter = new TableRowSorter(publisherTableModel);
        return this.publisherTableRowSorter;
    }

    private void btnAddPublisherActionPerformed(ActionEvent e) {
        PublisherEditForm editForm = new PublisherEditForm((Frame) getOwner());
        editForm.setLocationByPlatform(true);
        editForm.setVisible(true);

        publisherTableModel.refresh();
    }

    private void btnSearchActionPerformed(ActionEvent e) {
        String query = txtSearchQuery.getText();
        this.publisherTableRowSorter.setRowFilter(RowFilter.regexFilter(query));
    }

    private void btnResetSearchActionPerformed(ActionEvent e) {
        this.publisherTableRowSorter.setRowFilter(RowFilter.regexFilter(""));
    }

    private void popupMenuPublisherDeleteActionPerformed(ActionEvent e) {
        int selectedRow = tabPublishers.getSelectedRow();
        if (selectedRow >= 0) {
            this.publisherTableModel.remove(selectedRow);
        }
    }

    protected JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenuPublisherDeleteActionPerformed(e);
            }
        });
        return popupMenu;
    }
}
