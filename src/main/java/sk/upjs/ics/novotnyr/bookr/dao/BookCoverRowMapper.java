package sk.upjs.ics.novotnyr.bookr.dao;

import org.springframework.jdbc.core.RowMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCoverRowMapper implements RowMapper<ImageIcon> {

    @Override
    public ImageIcon mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            InputStream binaryStream = rs.getBinaryStream("cover");
            BufferedImage bufferedImage = ImageIO.read(binaryStream);
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            throw new SQLException("Unable to convert image cover data", e);
        }
    }
}
