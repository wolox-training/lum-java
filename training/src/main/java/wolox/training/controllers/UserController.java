package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import wolox.training.exceptions.users.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an id, returns a book", response = Users.class)
    public Users read(@PathVariable Long id) {
        try {
            Optional<Users> users = userRepository.findById(id);
            return users.orElse(null);
        } catch(ResponseStatusException e) {
            throw new UserNotFoundException(e);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Giving a user and id, updates given user", response = Users.class)
    public Users update(
        @ApiParam(value = "User to update", required = true) @RequestBody Users users,
        @ApiParam(value = "Id to find user") @PathVariable Long id
    ) {
        if (!users.getUsername().equals(id)) {
            throw new BookIsbnMismatchException();
        }
        exists(id);
        return userRepository.save(users);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes selected user")
    public void delete(
        @ApiParam(value = "Username to find user", required = true) @PathVariable Long id
    ) {
        exists(id);
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}/add")
    @ApiOperation(value = "Giving an id and book, ads that book to user")
    public void addBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value= "Id to find user", required = true) @PathVariable Long id
    ) {
        Users user = read(id);
        if (bookAlreadyExists(user, book)) {
            user.addBook(book);
            update(user, id);
        } else {
            throw new BookAlreadyOwnException();
        }
    }

    @PutMapping("/{username}/remove")
    @ApiOperation(value = "Giving an username and book, removes that book from user")
    public void removeBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find user", required = true) @PathVariable Long id
    ) {
        Users user = read(id);
        if (!bookAlreadyExists(user, book)) {
            user.removeBook(book);
            update(user, id);
        } else {
            throw new BookNotOwnedException();
        }

    }

    private void exists(Long id) {
        try {
            userRepository.findById(id);
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
