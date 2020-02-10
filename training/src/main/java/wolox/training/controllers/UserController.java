package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import wolox.training.exceptions.book.BookAlreadyOwnException;
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
    @ApiOperation(value = "Giving an id, returns a book", response = Users.class)
    public Users read(
        @ApiParam(value = "Id to find user") @PathVariable long id
    ) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PutMapping("api/users/{id}")
    @ApiOperation(value = "Giving a user and id, updates given user", response = Users.class)
    public Users update(
        @ApiParam(value = "User to update", required = true) @RequestBody Users users,
        @ApiParam(value = "Id to find user") @PathVariable Long id
    ) {
        if (users.getId() != id ) {
            throw new UserIdMismatchException();
        }
        read(id);
        return userRepository.save(users);
    }

    @DeleteMapping("api/users/{id}")
    @ApiOperation(value = "Giving an id, deletes selected user")
    public void delete(
        @ApiParam(value = "Username to find user", required = true) @PathVariable Long id
    ) {
        read(id);
        userRepository.deleteById(id);
    }

    @PutMapping("api/users/{id}/add")
    @ApiOperation(value = "Giving an id and book, ads that book to user")
    public void addBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value= "Id to find user", required = true) @PathVariable Long id
    ) {
        Users user = read(id);
        if (!bookAlreadyExists(user, book)) {
            user.addBook(book);
            update(user, id);
        } else {
            throw new BookAlreadyOwnException();
        }
    }

    @PutMapping("/api/users/{id}/remove")
    @ApiOperation(value = "Giving an username and book, removes that book from user")
    public void removeBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find user", required = true) @PathVariable Long id
    ) {
        Users user = read(id);
        if (bookAlreadyExists(user, book)) {
            user.removeBook(book);
            update(user, id);
        } else {
            throw new BookNotOwnedException();
        }
    }

    private boolean bookAlreadyExists(Users users, Book book) {
        boolean isBookAlreadyAdded = false;
        for (Book element : users.getBooks()) {
            if (element.equals(book)) {
                isBookAlreadyAdded = true;
                break;
            }
        }
        return isBookAlreadyAdded;
    }
}
