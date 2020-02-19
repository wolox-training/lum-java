package wolox.training.exceptions.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException() {
        super(HttpStatus.NOT_FOUND, "The given id doesn't represent book in DB");
    }
}
