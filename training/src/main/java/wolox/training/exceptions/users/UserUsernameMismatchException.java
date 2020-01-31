package wolox.training.exceptions.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUsernameMismatchException extends ResponseStatusException {

    public UserUsernameMismatchException() {
        super(HttpStatus.BAD_REQUEST, "User's username mismatch username given");
    }
}
