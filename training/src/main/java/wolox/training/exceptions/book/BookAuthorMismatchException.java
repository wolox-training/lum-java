package wolox.training.exceptions.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAuthorMismatchException extends ResponseStatusException {

    public BookAuthorMismatchException() {
        super(HttpStatus.NOT_FOUND, "The given author doesn't match book in DB");
    }
}
