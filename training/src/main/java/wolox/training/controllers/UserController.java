package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Giving an user, creates an user", response = Users.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User created succesfully"),
        @ApiResponse(code = 500, message = "One or more fields are invalid")
    })
    public Users create(
        @ApiParam(value = "User to be creted", required = true) @RequestBody Users users
    ) {
        return userService.createUser(users);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User returned succesfully"),
        @ApiResponse(code = 404, message = "User not found")
    })
    @ApiOperation(value = "Giving an id, returns an user", response = Users.class)
    public Users read(
        @ApiParam(value = "Id to find user", required = true) @PathVariable long id
    ) {
        return userService.readUser(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Giving an id and username, updates given user", response = Users.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User updated succesfully"),
        @ApiResponse(code = 400, message = "User's id mismatches id given"),
        @ApiResponse(code = 404, message = "User not found")
    })
    public Users update(
        @ApiParam(value = "User to update", required = true) @RequestBody Users users,
        @ApiParam(value = "Id to find user") @PathVariable long id)
    {
        return userService.updateUsers(users, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes selected user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User deleted succesfully"),
        @ApiResponse(code = 400, message = "User's id mismatches id given"),
        @ApiResponse(code = 404, message = "User not found")
    })
    public void delete(
        @ApiParam(value = "Id to find user", required = true) @PathVariable long id)
    {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/add")
    @ApiOperation(value = "Giving an id and book, ads that book to user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book added to user succesfully"),
        @ApiResponse(code = 400, message = "Book already own"),
        @ApiResponse(code = 404, message = "User not found")
    })
    public void addBook(
        @ApiParam(value = "Book to be added", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find user", required = true) @PathVariable long id
    ) {
        userService.addBook(book, id);
    }

    @PutMapping("/{id}/remove")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book removed from user succesfully"),
        @ApiResponse(code = 400, message = "Book not owned"),
        @ApiResponse(code = 404, message = "User not found")
    })
    public void removeBook(
        @ApiParam(value = "Book to be removed", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find user", required = true) @PathVariable long id
    ) {
        userService.removeBook(book, id);
    }

}
