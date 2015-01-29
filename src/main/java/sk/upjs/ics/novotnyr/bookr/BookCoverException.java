package sk.upjs.ics.novotnyr.bookr;


public class BookCoverException extends RuntimeException {

    public BookCoverException() {
    }

    public BookCoverException(String message) {
        super(message);
    }

    public BookCoverException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookCoverException(Throwable cause) {
        super(cause);
    }

}
