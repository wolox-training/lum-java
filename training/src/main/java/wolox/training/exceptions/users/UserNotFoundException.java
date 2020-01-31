package wolox.training.exceptions.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(Throwable err) {
        super(HttpStatus.NOT_FOUND, "Given username doesn't represent an user in DB", err);
    }
}
