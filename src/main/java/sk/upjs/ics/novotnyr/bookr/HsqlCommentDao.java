package sk.upjs.ics.novotnyr.bookr;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sk.upjs.ics.novotnyr.bookr.dao.CommentRowMapper;
import sk.upjs.ics.novotnyr.bookr.dao.SqlQueries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HsqlCommentDao implements CommentDao {
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CommentRowMapper commentRowMapper = new CommentRowMapper();

    public HsqlCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }

    @Override
    public List<Comment> list(Book book) {
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_BOOK_COMMENTS, commentRowMapper, book.getId());
    }

    @Override
    public Comment findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SqlQueries.SELECT_COMMENT_BY_ID, commentRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Comment comment) {
        if (comment.getId() == null) {
            save(comment);
        } else {
            update(comment);
        }
    }

    private void save(Comment comment) {
        Map<String, Object> insertMap = new HashMap<String, Object>();
        insertMap.put("id", comment.getId());
        insertMap.put("book_id", comment.getBook().getId());
        insertMap.put("comment", comment.getComment());
        insertMap.put("rating", comment.getRating());

        String sql = "INSERT INTO comment VALUES(:id, :book_id, :comment, :rating)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(insertMap), keyHolder);
        Long id = keyHolder.getKey().longValue();
        comment.setId(id);

    }

    private void update(Comment comment) {
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("id", comment.getId());
        updateMap.put("book_id", comment.getBook().getId());
        updateMap.put("comment", comment.getComment());
        updateMap.put("rating", comment.getRating());

        String sql = "UPDATE comment SET book_id = :book_id, comment = :comment, rating = :rating WHERE id = :id";

        namedParameterJdbcTemplate.update(sql, updateMap);
    }

    @Override
    public void delete(Comment comment) {
        String sql = "DELETE FROM comment WHERE id = ?";
        jdbcTemplate.update(sql, comment.getId());
    }


}
