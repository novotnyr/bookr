package sk.upjs.ics.novotnyr.bookr;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sk.upjs.ics.novotnyr.bookr.dao.BookRowMapper;
import sk.upjs.ics.novotnyr.bookr.dao.SqlQueries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HsqlBookDao implements BookDao {
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private BookRowMapper bookRowMapper = new BookRowMapper();

    public HsqlBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }

    @Override
    public List<Book> list() {
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_BOOKS, bookRowMapper);
    }

    @Override
    public Book findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SqlQueries.SELECT_BOOK_BY_ID, bookRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Book book) {
        if (book.getId() == null) {
            save(book);
        } else {
            update(book);
        }
    }

    private void save(Book book) {
        Map<String, Object> insertMap = new HashMap<String, Object>();
        insertMap.put("id", book.getId());
        insertMap.put("title", book.getTitle());
        insertMap.put("path", "file://null");
        insertMap.put("publisher_id", book.getPublisher().getId());
        insertMap.put("year", book.getYear());

        String sql = "INSERT INTO book VALUES(:id, :title, :path, :publisher_id, :year)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(insertMap), keyHolder);
        Long id = keyHolder.getKey().longValue();
        book.setId(id);

    }

    private void update(Book book) {
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("id", book.getId());
        updateMap.put("title", book.getTitle());
        updateMap.put("publisher_id", book.getPublisher().getId());
        updateMap.put("year", book.getYear());

        String sql = "UPDATE book SET title = :title, publisher_id = :publisher_id, year = :year WHERE id = :id";

        namedParameterJdbcTemplate.update(sql, updateMap);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return jdbcTemplate.query(SqlQueries.SELECT_BOOK_BY_TITLE_SUBSTRING, bookRowMapper, title);
    }

    @Override
    public void delete(Book book) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, book.getId());
    }

    @Override
    public List<Book> findByPublisher(Publisher publisher) {
        return jdbcTemplate.query(SqlQueries.SELECT_BOOK_BY_PUBLISHER, bookRowMapper, publisher.getId());
    }


}
