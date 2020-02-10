package wolox.training.exceptions.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserIdMismatchException extends ResponseStatusException {

    public UserIdMismatchException() {
        super(HttpStatus.BAD_REQUEST, "User's id mismatch id given");
    }
}
