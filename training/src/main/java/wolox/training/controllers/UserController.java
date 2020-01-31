package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.book.BookAlreadyOwnException;
import wolox.training.exceptions.book.BookIsbnMismatchException;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.exceptions.book.BookNotOwnedException;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public Users read(@PathVariable String username) {
        try {
            Optional<Users> users = userRepository.findById(username);
            return users.orElse(null);
        } catch(ResponseStatusException e) {
            throw new BookNotFoundException(e);
        }
    }

    @PutMapping("/{username}")
    public Users update(@RequestBody Users users, @PathVariable String username) {
        if (!users.getUsername().equals(username)) {
            throw new BookIsbnMismatchException();
        }
        exists(username);
        return userRepository.save(users);
    }

    @DeleteMapping("/{username}")
    public void delete(@PathVariable String usernamme) {
        exists(usernamme);
        userRepository.deleteById(usernamme);
    }

    @PutMapping("/{username}/add")
    public void addBook(@RequestBody Book book, @PathVariable String username) {
        Users user = read(username);
        if (bookAlreadyExists(user, book)) {
            user.addBook(book);
            update(user, username);
        } else {
            throw new BookAlreadyOwnException();
        }
    }

    @PutMapping("/{username}/remove")
    public void removeBook(@RequestBody Book book, @PathVariable String username) {
        Users user = read(username);
        if (!bookAlreadyExists(user, book)) {
            user.removeBook(book);
            update(user, username);
        } else {
            throw new BookNotOwnedException();
        }

    }

    private void exists(String isbn) {
        try {
            userRepository.findById(isbn);
        } catch (ResponseStatusException e) {
            throw new BookNotFoundException(e);
        }
    }

    private boolean bookAlreadyExists(Users users, Book book) {
        boolean isAlreadySet = false;
        for (Book element : users.getBooks()) {
            if (element == book) {
                isAlreadySet = true;
                break;
            }
        }
        return isAlreadySet;
    }
}
