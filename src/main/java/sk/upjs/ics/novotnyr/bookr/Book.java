package sk.upjs.ics.novotnyr.bookr;

import java.io.File;
import java.util.List;

public class Book {
    private CommentDao commentDao = BeanFactory.INSTANCE.commentDao();

    private Long id;

    private String title;

    private File path;

    private Publisher publisher;

    private int year;

    private int rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        // nastavuje ho BookRowMapper!
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return commentDao.list(this);
    }

}
