package sk.upjs.ics.novotnyr.bookr;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HsqlPublisherDao implements PublisherDao {
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<Publisher> publisherRowMapper = BeanPropertyRowMapper.newInstance(Publisher.class);

    public HsqlPublisherDao() {
        // empty constructor
    }

    public HsqlPublisherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }

    @Override
    public List<Publisher> list() {
        return jdbcTemplate.query("SELECT * FROM publisher", publisherRowMapper);
    }

    @Override
    public void saveOrUpdate(Publisher publisher) {
        if (publisher.getId() == null) {
            save(publisher);
        } else {
            update(publisher);
        }
    }

    private void save(Publisher publisher) {
        Map<String, Object> insertMap = new HashMap<String, Object>();
        insertMap.put("id", null);
        insertMap.put("name", publisher.getName());
        insertMap.put("web", publisher.getWeb().toString());

        String sql = "INSERT INTO publisher VALUES(:id, :name, :web)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(insertMap), keyHolder);
        Long id = keyHolder.getKey().longValue();
        publisher.setId(id);
    }

    private void update(Publisher publisher) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Publisher publisher) {
        jdbcTemplate.update("DELETE FROM publisher WHERE id = ?", publisher.getId());
    }


}
