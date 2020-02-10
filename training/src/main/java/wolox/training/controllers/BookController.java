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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.book.BookAuthorMismatchException;
import wolox.training.exceptions.book.BookIdMismatchException;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController("api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/api/books/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @PostMapping("api/books")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Giving a book, creates a book", response = Book.class)
    public Book create(
        @ApiParam(value = "Book object") @RequestBody Book book) {
        return bookRepository.save(Preconditions.checkNotNull(book));
    }

    @GetMapping("/api/books/{id}")
    @ApiOperation(value = "Giving an Id, returns a book", response = Book.class)
    public Book read(
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PutMapping("/api/books/{id}")
    @ApiOperation(value = "Giving an id and a book, updates a book", response = Book.class)
    public Book update(
        @ApiParam(value = "Book object", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        read(id);
        return bookRepository.save(book);
    }


    @DeleteMapping("/api/books/{id}")
    @ApiOperation(value = "Giving an isbn, deletes a book")
    public void delete(
        @ApiParam(value = "Isbn to find book", required = true) @PathVariable long id
    ) {
        read(id);
        bookRepository.deleteById(id);
    }

    @GetMapping("/api/books")
    public Book findByAuthor(@RequestParam(name="author", required=false) String author) {
        return bookRepository.findFirstByAuthor(author).orElseThrow(BookAuthorMismatchException::new);
    }
}
