package wolox.training.controllers;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.List;
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
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OpenLibraryService openLibraryService;

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
        @ApiParam(value = "Author's name") @RequestParam(name="author") String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/h")
    @ApiParam(value = "Giving a publisher, genre and year, returns a book")
    public List<Book> findByPublisherAndGenreAndYear(
        @ApiParam(value = "Book's publisher to find book") @RequestParam(name="publisher") String publisher,
        @ApiParam(value = "Book's genreAuthor's name") @RequestParam(name="genre") String genre,
        @ApiParam(value = "Book's year") @RequestParam(name="year") String year
    ) {
        return bookService.findByPublisherAndGenreAndYear(publisher, genre, year);
    }

    @GetMapping("/isbn/{isbn}")
    @ApiParam(value = "Giving an isbn, returns a book from OpenLibrary.org")
    public Book findByIsbn(
        @ApiParam(value = "Book's isbn to find book") @PathVariable String isbn
    ) throws IOException {
        try {
            return bookService.findByIsbn(isbn);
        } catch (Exception e) {
            return create(openLibraryService.findBook(isbn));
        }
    }

}
