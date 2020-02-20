package wolox.training.controllers;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Users create(@RequestBody Users users) {
        return userService.createUser(users);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an username, returns a book", response = Users.class)
    public Users read(@PathVariable long id) {
        return userService.readUser(id);
    }

    @ApiOperation(value = "Giving a user and username, updates given user", response = Users.class)
    @PutMapping("/{id}")
    public Users update(
        @ApiParam(value = "User to update", required = true) @RequestBody Users users,
        @ApiParam(value = "Id to find user") @PathVariable long id)
    {
        return userService.updateUsers(users, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes selected user")
    public void delete(
        @ApiParam(value = "Username to find user", required = true) @PathVariable long id)
    {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Giving an id and book, ads that book to user")
    @PutMapping("/{id}/add")
    public void addBook(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @PathVariable long id) {
        userService.addBook(book, id);
    }

    @PutMapping("/{id}/remove")
    public void removeBook(@RequestBody Book book, @PathVariable long id) {
        userService.removeBook(book, id);
    }

    @GetMapping
    @ApiOperation(value = "Giving part of name, returns users")
    public List<Users> getBetweenDatesAndHaveInUsername(
        @ApiParam(value = "Begining date of birthday", required = true) @RequestParam(name="startDate") String startDate,
        @ApiParam(value = "End date of birthdate", required = true) @RequestParam(name="endDate") String endDate,
        @ApiParam(value = "Part of name", required = true) @RequestParam(name="name") String name
    ) {
        return userService.getBetweenDatesAndHaveInUsername(
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            name);
    }

    @GetMapping("/h")
    @ApiOperation(value = "Giving part of name, returns users")
    public List<Users> getBetweenDatesAndHaveInUsername2(
        @ApiParam(value = "Begining date of birthday", required = true)
        @RequestParam(name="startDate", defaultValue = "0000-01-01", required = false)
            String startDate,
        @ApiParam(value = "End date of birthdate", required = true)
        @RequestParam(name="endDate", defaultValue = "9999-01-01",required = false)
            String endDate,
        @ApiParam(value = "Part of name", required = true)
        @RequestParam(name="namePart", defaultValue = "", required = false)
            String name
    ) {
        return userService.getBetweenDatesAndHaveInUsername2(
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            name);
    }

}
