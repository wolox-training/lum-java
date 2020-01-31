package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyOwnException extends ResponseStatusException {

    public BookAlreadyOwnException() {
        super(HttpStatus.NOT_FOUND, "Given book is already asociated with user");
    }

}
