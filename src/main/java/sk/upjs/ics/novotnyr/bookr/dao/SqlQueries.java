package sk.upjs.ics.novotnyr.bookr.dao;


public class SqlQueries {
    public static final String SELECT_ALL_BOOKS = "SELECT \n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "\n" +
            "FROM book\n" +
            "JOIN publisher on publisher.id = book.publisher_id";

    public static final String SELECT_BOOK_BY_ID = "SELECT \n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "\n" +
            "FROM book\n" +
            "JOIN publisher on publisher.id = book.publisher_id\n" +
            "WHERE book.id = ?";

    public static final String SELECT_BOOK_BY_TITLE_SUBSTRING = "SELECT \n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "\n" +
            "FROM book\n" +
            "JOIN publisher on publisher.id = book.publisher_id\n" +
            "WHERE LOWER(book.title) LIKE LOWER('%' || ? || '%')";

    public static final String SELECT_BOOK_BY_PUBLISHER = "SELECT \n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "\n" +
            "FROM book\n" +
            "JOIN publisher on publisher.id = book.publisher_id\n" +
            "WHERE publisher.id = ?";

    public static final String SELECT_ALL_BOOK_COMMENTS = "SELECT \n" +
            "   comment.id as comment_id,\n" +
            "   comment.comment as comment_comment,\n" +
            "   comment.rating as comment_rating,\n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "FROM comment \n" +
            "JOIN book ON book.id = comment.book_id \n" +
            "JOIN publisher on publisher.id = book.publisher_id \n" +
            "WHERE comment.book_id = ?\n";

    public static final String SELECT_COMMENT_BY_ID = "SELECT \n" +
            "   comment.id as comment_id,\n" +
            "   comment.comment as comment_comment,\n" +
            "   comment.rating as comment_rating,\n" +
            "   book.id as book_id,\n" +
            "	book.title as book_title,\n" +
            "	book.path as book_path,\n" +
            "	book.year as book_year,\n" +
            "   (SELECT AVG(comment.rating) FROM comment WHERE comment.book_id = book.id GROUP BY book.id) as book_rating,\n" +
            "	publisher.id as publisher_id,\n" +
            "	publisher.name as publisher_name,\n" +
            "	publisher.web as publisher_web\n" +
            "FROM comment \n" +
            "JOIN book ON book.id = comment.book_id \n" +
            "JOIN publisher on publisher.id = book.publisher_id \n" +
            "WHERE comment.id = ?";
}
