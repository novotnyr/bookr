package sk.upjs.ics.novotnyr.bookr;


import java.util.List;

public interface BookDao {
    List<Book> list();

    Book findById(Long id);

    public void saveOrUpdate(Book book);

    public List<Book> searchByTitle(String title);

    public void delete(Book book);

    List<Book> findByPublisher(Publisher publisher);
}
