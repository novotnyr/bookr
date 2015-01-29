
package sk.upjs.ics.novotnyr.bookr;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HsqlBookDaoTest {
    private BookDao bookDao = BeanFactory.INSTANCE.bookDao();


    @Test
    public void testList() {
        List<Book> books = bookDao.list();
        List<Book> books1 = bookDao.list();
        assertEquals(books1.size(), books.size());
    }

    @Test
    public void testFindByNonexistentId() {
        Book book = bookDao.findById(-1L);
        assertNull(book);
    }

}