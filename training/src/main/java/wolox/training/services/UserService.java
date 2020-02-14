package wolox.training.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.book.BookAlreadyOwnException;
import wolox.training.exceptions.book.BookNotOwnedException;
import wolox.training.exceptions.users.UserIdMismatchException;
import wolox.training.exceptions.users.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users createUser(Users users) {
        log.debug(String.format("%s was received to POST", users.toString()));
        return userRepository.save(users);
    }

    public Users readUser(long id) {
        log.debug(String.format("%d was received to search Users",id));
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Users updateUsers(Users users, long id) {
        log.debug(String.format("%s (users) and %d (id) was received to look for User",users,id));
        if (users.getId() != id ) {
            log.error("Users's id mismatches id");
            throw new UserIdMismatchException();
        }
        readUser(id);
        log.debug("User was found");
        return userRepository.save(users);
    }

    public void deleteUser(long id) {
        log.debug(String.format("%d was received to delete user", id));
        readUser(id);
        log.debug("User was found");
        userRepository.deleteById(id);
    }

    public void addBook(Book book, long id) {
        log.debug(String.format("%s (book) and %d (id) was received to add book to user",book,id));
        Users user = readUser(id);
        if (!bookAlreadyExists(user, book)) {
            user.addBook(book);
            updateUsers(user, id);
            log.debug("Book was added");
        } else {
            log.error("Book was already owned");
            throw new BookAlreadyOwnException();
        }
    }

    public void removeBook(Book book, long id) {
        log.debug(String.format("%s (book) and %d (id) was received to remove book to user",book,id));
        Users user = readUser(id);
        if (bookAlreadyExists(user, book)) {
            user.removeBook(book);
            updateUsers(user, id);
            log.debug("Book was removed");
        } else {
            log.error("Book was not owned");
            throw new BookNotOwnedException();
        }
    }

    private boolean bookAlreadyExists(Users users, Book book) {
        boolean isBookAlreadyAdded = false;
        for (Book element : users.getBooks()) {
            element.setId(0);
            if (element.equals(book)) {
                isBookAlreadyAdded = true;
                break;
            }
        }
        return isBookAlreadyAdded;
    }

}
