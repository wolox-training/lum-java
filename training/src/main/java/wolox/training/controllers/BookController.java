package wolox.training.controllers;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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
import wolox.training.services.BookService;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Giving a book, creates a book", response = Book.class)
    public Book create(
        @ApiParam(value = "Book object") @RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an id, returns a book", response = Book.class)
    public Book read(
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        return bookService.readBook(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Giving an id and a book, updates give book", response = Book.class)
    public Book update(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        return bookService.updateBook(book, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Giving an id, deletes a book")
    public void delete(
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        bookService.deleteBook(id);
    }

    @GetMapping
    @ApiOperation(value = "Giving an author, returns a book")
    public Book findByAuthor(
        @ApiParam(value = "Author's name") @RequestParam(name="author", required=false) String author) {
        return bookService.findByAuthor(author);
    }

}
