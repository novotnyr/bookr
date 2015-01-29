package sk.upjs.ics.novotnyr.bookr.dao;

import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.novotnyr.bookr.Book;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookRowMapper implements RowMapper<Book> {
    private PublisherRowMapper publisherRowMapper = new PublisherRowMapper();

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("book_id"));
        book.setTitle(rs.getString("title"));
        book.setYear(rs.getInt("book_year"));
        book.setRating(rs.getInt("book_rating"));

        book.setPublisher(publisherRowMapper.mapRow(rs, rowNum));

        return book;
    }

}
