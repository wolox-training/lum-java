package wolox.training.controllers;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import wolox.training.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users users) {
        return userService.createUser(users);
    }

    @GetMapping("/{id}")
    public Users read(@PathVariable long id) {
        return userService.readUser(id);
    }

    @PutMapping("/{id}")
    public Users update(@RequestBody Users users, @PathVariable long id) {
        return userService.updateUsers(users, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/add")
    public void addBook(@RequestBody Book book, @PathVariable long id) {
        userService.addBook(book, id);
    }

    @PutMapping("/{id}/remove")
    public void removeBook(@RequestBody Book book, @PathVariable long id) {
        userService.removeBook(book, id);
    }


}
