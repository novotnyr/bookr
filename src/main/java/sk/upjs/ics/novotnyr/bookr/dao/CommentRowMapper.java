package sk.upjs.ics.novotnyr.bookr.dao;

import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.novotnyr.bookr.Book;
import sk.upjs.ics.novotnyr.bookr.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CommentRowMapper implements RowMapper<Comment> {

    private BookRowMapper bookRowMapper = new BookRowMapper();

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getLong("comment_id"));
        comment.setBook(bookRowMapper.mapRow(rs, rowNum));
        comment.setComment(rs.getString("comment_comment"));
        comment.setRating(rs.getInt("comment_rating"));

        return comment;
    }

}
