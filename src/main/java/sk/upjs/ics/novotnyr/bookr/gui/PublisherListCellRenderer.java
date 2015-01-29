package sk.upjs.ics.novotnyr.bookr.gui;

import sk.upjs.ics.novotnyr.bookr.Publisher;

import javax.swing.*;
import java.awt.*;


public class PublisherListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Publisher publisher = (Publisher) value;
        if (Publisher.isNullPublisher(publisher)) {
            publisher.setName("-- Select -- ");
        }
        return super.getListCellRendererComponent(list, publisher.getName(), index, isSelected, cellHasFocus);
    }

}
