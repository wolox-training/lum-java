package wolox.training.controllers;

import com.google.common.base.Preconditions;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    private static final String POSSITIVE_ID_MESSAGE = "Book's id must be non negative";

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users users) {
        return userService.createUser(Preconditions.checkNotNull(users));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an username, returns a book", response = Users.class)
    public Users read(@PathVariable long id) {
        Preconditions.checkArgument(id >= 0, POSSITIVE_ID_MESSAGE);
        return userService.readUser(id);
    }

    @ApiOperation(value = "Giving a user and username, updates given user", response = Users.class)
    @PutMapping("/{id}")
    public Users update(
        @ApiParam(value = "User to update", required = true) @RequestBody Users users,
        @ApiParam(value = "Id to find user") @PathVariable long id)
    {
        Preconditions.checkArgument(id >= 0, POSSITIVE_ID_MESSAGE);
        return userService.updateUsers(Preconditions.checkNotNull(users), id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes selected user")
    public void delete(
        @ApiParam(value = "Username to find user", required = true) @PathVariable long id)
    {
        Preconditions.checkArgument(id >= 0, POSSITIVE_ID_MESSAGE);
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Giving an id and book, ads that book to user")
    @PutMapping("/{id}/add")
    public void addBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @PathVariable long id) {
        Preconditions.checkArgument(id >= 0, POSSITIVE_ID_MESSAGE);
        userService.addBook(Preconditions.checkNotNull(book), id);
    }

    @PutMapping("/{id}/remove")
    public void removeBook(@RequestBody Book book, @PathVariable long id) {
        Preconditions.checkArgument(id >= 0, POSSITIVE_ID_MESSAGE);
        userService.removeBook(Preconditions.checkNotNull(book), id);
    }

}
