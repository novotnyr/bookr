package sk.upjs.ics.novotnyr.bookr.gui;

import sk.upjs.ics.novotnyr.bookr.BeanFactory;
import sk.upjs.ics.novotnyr.bookr.Publisher;
import sk.upjs.ics.novotnyr.bookr.PublisherDao;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

public class PublisherTableModel extends AbstractTableModel {
    private static final int PUBLISHER_COLUMN_COUNT = 2;

    private static final int COLUMN_INDEX_NAME = 0;

    private static final int COLUMN_INDEX_WEB = 1;

    private static final String[] COLUMN_NAMES = {"Name", "Web"};

    private PublisherDao publisherDao = BeanFactory.INSTANCE.publisherDao();

    private List<Publisher> publishers = new LinkedList<Publisher>();

    @Override
    public int getRowCount() {
        return publishers.size();
    }

    @Override
    public int getColumnCount() {
        return PUBLISHER_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Publisher publisher = publishers.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_INDEX_NAME:
                return publisher.getName();
            case COLUMN_INDEX_WEB:
                return publisher.getWeb();
        }
        return "???";
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public Publisher getValueAt(int rowIndex) {
        return publishers.get(rowIndex);
    }

    public void remove(int rowIndex) {
        publisherDao.delete(getValueAt(rowIndex));
        refresh();
    }

    public void refresh() {
        publishers.clear();
        publishers.addAll(publisherDao.list());

        fireTableDataChanged();
    }

}
