package sk.upjs.ics.novotnyr.bookr.gui;

import sk.upjs.ics.novotnyr.bookr.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookDetailPanel {
    private JButton btnComments;
    private JLabel lblTitle;
    private JLabel lblPublisher;
    private JLabel lblYear;
    private JLabel lblRating;
    private JPanel panel;
    private Book book;

    public BookDetailPanel() {
        btnComments.setVisible(false);
        btnComments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookDetailForm(book);
            }
        });
    }

    public void setBook(Book book) {
        lblTitle.setText(book.getTitle());
        lblPublisher.setText(book.getPublisher().getName());
        lblYear.setText(Integer.toString(book.getYear()));
        lblRating.setText("Rating: " + Integer.toString(book.getRating()));
        this.book = book;
        btnComments.setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }
}
