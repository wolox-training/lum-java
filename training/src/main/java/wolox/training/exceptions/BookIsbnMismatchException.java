package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookIsbnMismatchException extends ResponseStatusException {

    public BookIsbnMismatchException() {
        super(HttpStatus.BAD_REQUEST, "Book's isbn mismatch isbn given");
    }
}
