package sk.upjs.ics.novotnyr.bookr.dao;

import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.novotnyr.bookr.Publisher;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PublisherRowMapper implements RowMapper<Publisher> {

    @Override
    public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("publisher_id"));
            publisher.setName(rs.getString("publisher_name"));
            publisher.setWeb(new URL(rs.getString("publisher_web")));

            return publisher;
        } catch (MalformedURLException e) {
            throw new SQLException("Unable to map publisher URL String to URL");
        }
    }

}
