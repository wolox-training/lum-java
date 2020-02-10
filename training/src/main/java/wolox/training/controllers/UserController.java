package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.book.BookAlreadyOwnException;
import wolox.training.exceptions.book.BookIdMismatchException;
import wolox.training.exceptions.book.BookIsbnMismatchException;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.exceptions.book.BookNotOwnedException;
import wolox.training.exceptions.users.UserIdMismatchException;
import wolox.training.exceptions.users.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@RestController("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users users) {
        return userRepository.save(users);
    }

    @GetMapping("api/users/{id}")
    public Users read(@PathVariable long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PutMapping("api/users/{id}")
    public Users update(@RequestBody Users users, @PathVariable long id) {
        if (users.getId() != id ) {
            throw new UserIdMismatchException();
        }
        read(id);
        return userRepository.save(users);
    }

    @DeleteMapping("api/users/{id}")
    public void delete(@PathVariable long id) {
        read(id);
        userRepository.deleteById(id);
    }

    @PutMapping("api/users/{username}/add")
    public void addBook(@RequestBody Book book, @PathVariable long id) {
        Users user = read(id);
        if (bookAlreadyExists(user, book)) {
            user.addBook(book);
            update(user, id);
        } else {
            throw new BookAlreadyOwnException();
        }
    }

    @PutMapping("api/users/{username}/remove")
    public void removeBook(@RequestBody Book book, @PathVariable long id) {
        Users user = read(id);
        if (!bookAlreadyExists(user, book)) {
            user.removeBook(book);
            update(user, id);
        } else {
            throw new BookNotOwnedException();
        }
    }

    private boolean bookAlreadyExists(Users users, Book book) {
        boolean isBookAlreadyAdded = false;
        for (Book element : users.getBooks()) {
            if (element == book) {
                isBookAlreadyAdded = true;
                break;
            }
        }
        return isBookAlreadyAdded;
    }
}
