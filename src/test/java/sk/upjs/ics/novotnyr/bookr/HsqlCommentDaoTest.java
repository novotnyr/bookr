package sk.upjs.ics.novotnyr.bookr;

import org.junit.Assert;
import org.junit.Test;

public class HsqlCommentDaoTest {
    CommentDao commentDao = BeanFactory.INSTANCE.commentDao();

    @Test
    public void createDeleteTest() {
        Book newBook = new Book();
        newBook.setId(0L);
        Comment newComment = new Comment();
        newComment.setBook(newBook);
        newComment.setComment("skuska");
        newComment.setRating(5);
        commentDao.saveOrUpdate(newComment);

        Comment savedComment = commentDao.findById(newComment.getId());

        Assert.assertTrue(newComment.getComment().equals(savedComment.getComment()));
        Assert.assertTrue(newComment.getRating() == savedComment.getRating());

        commentDao.delete(newComment);

        Comment deletedComment = commentDao.findById(newComment.getId());

        Assert.assertNull(deletedComment);
    }
}
