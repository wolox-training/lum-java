package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException(Throwable err) {
        super(HttpStatus.NOT_FOUND, "Given isbn doesn't represent book in DB", err);
    }
}
