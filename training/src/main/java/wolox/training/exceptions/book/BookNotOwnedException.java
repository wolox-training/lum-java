package wolox.training.exceptions.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotOwnedException extends ResponseStatusException {

    public BookNotOwnedException() {
        super(HttpStatus.NOT_FOUND, "Given user doesn't own selected book");
    }

}
