package sk.upjs.ics.novotnyr.bookr.gui;

import sk.upjs.ics.novotnyr.bookr.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookDetailForm {
    private CommentDao commentDao = BeanFactory.INSTANCE.commentDao();
    private BookDao bookDao = BeanFactory.INSTANCE.bookDao();

    private JPanel panel;
    private JLabel lblTitle;
    private JLabel lblPublisher;
    private JLabel lblYear;
    private JButton btnComments;
    private JLabel lblRating;
    private JTable tblComments;
    private JTextField txtComment;
    private JButton btnAddComment;
    private JComboBox cbxRating;
    private JDialog dialog;

    private Book book;
    private MyTableModel myTableModel;

    public BookDetailForm(Book book) {

        this.book = book;
        dialog = new JDialog(GuiFactory.INSTANCE.bookDashboardForm(), "Book detail", true);

        myTableModel = new MyTableModel(book.getComments());
        tblComments.setModel(myTableModel);
        tblComments.setComponentPopupMenu(createTblCommentPopupMenu());
        tblComments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        refresh();

        btnAddComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddCommentActionPerformed(e);
            }
        });

        dialog.add(panel);
        dialog.setSize(640, 480);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private JPopupMenu createTblCommentPopupMenu() {

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupTblCommentsDeleteActionPerformed(e);
            }
        });
        return popupMenu;
    }

    private void popupTblCommentsDeleteActionPerformed(ActionEvent e) {
        Comment selectedComment = (Comment) tblComments.getValueAt(tblComments.getSelectedRow(), -1);
        if (selectedComment != null) {
            int result = DialogUtils.yesNoDialog(dialog,
                    "The comment will be deleted! "
                            + selectedComment.getComment());
            if (result == JOptionPane.YES_OPTION) {
                commentDao.delete(selectedComment);
            }
        }

        refresh();
        GuiFactory.INSTANCE.bookDashboardForm().refreshBookData();
    }

    private void refresh() {
        book = bookDao.findById(book.getId());
        lblTitle.setText(book.getTitle());
        lblPublisher.setText(book.getPublisher().getName());
        lblYear.setText(Integer.toString(book.getYear()));
        lblRating.setText("Rating: " + book.getRating());

        MyTableModel myTableModel = (MyTableModel) tblComments.getModel();
        myTableModel.setComments(book.getComments());
    }

    private void btnAddCommentActionPerformed(ActionEvent e) {
        if (txtComment.getText().equals(""))
            return;
        Comment newComment = new Comment();
        newComment.setComment(txtComment.getText());
        newComment.setRating(cbxRating.getSelectedIndex() + 1);
        newComment.setBook(book);
        commentDao.saveOrUpdate(newComment);
        txtComment.setText("");
        cbxRating.setSelectedIndex(0);

        refresh();
        GuiFactory.INSTANCE.bookDashboardForm().refreshBookData();
    }

    private class MyTableModel extends AbstractTableModel {

        private List<Comment> comments;

        public MyTableModel(List<Comment> comments) {
            this.comments = comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return comments.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Comment";
                case 1:
                    return "Rating";
            }
            return super.getColumnName(column);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Comment comment = comments.get(rowIndex);
            switch (columnIndex) {
                case -1:
                    return comment;
                case 0:
                    return comment.getComment();
                case 1:
                    return comment.getRating();
            }
            return "";
        }
    }
}
