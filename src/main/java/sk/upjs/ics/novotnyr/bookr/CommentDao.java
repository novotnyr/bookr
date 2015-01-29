package sk.upjs.ics.novotnyr.bookr;


import java.util.List;

public interface CommentDao {
    public List<Comment> list(Book book);

    public Comment findById(Long id);

    public void saveOrUpdate(Comment comment);

    public void delete(Comment comment);
}
