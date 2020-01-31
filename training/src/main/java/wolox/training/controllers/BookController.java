package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import wolox.training.exceptions.book.BookIsbnMismatchException;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

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
        return bookRepository.save(book);
    }

    @GetMapping("/{ísbn}")
    @ApiOperation(value = "Giving an isbn, returns a book", response = Book.class)
    public Book read(
        @ApiParam(value = "Isbn to find book", required = true) @PathVariable String isbn
    ) {
        try {
            Optional<Book> book = bookRepository.findById(isbn);
            return book.orElse(null);
        } catch(ResponseStatusException e) {
            throw new BookNotFoundException(e);
        }
    }

    @PutMapping("/{isbn}")
    @ApiOperation(value = "Giving an isbn and a book, updates give book", response = Book.class)
    public Book update(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value = "Isbn to find book", required = true) @PathVariable String isbn
    ) {
        if (!book.getIsbn().equals(isbn)) {
            throw new BookIsbnMismatchException();
        }
        exists(isbn);
        return bookRepository.save(book);
    }

    @DeleteMapping("/{isbn}")
    @ApiOperation(value = "Giving an isbn, deletes a book")
    public void delete(
        @ApiParam(value = "Isbn to find book", required = true) @PathVariable String isbn
    ) {
        exists(isbn);
        bookRepository.deleteById(isbn);
    }

    private void exists(String isbn) {
        try {
            bookRepository.findById(isbn);
        } catch (ResponseStatusException e) {
            throw new BookNotFoundException(e);
        }
    }
}
