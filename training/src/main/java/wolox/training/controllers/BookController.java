package wolox.training.controllers;

import java.io.FileNotFoundException;
import java.util.Optional;
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
import wolox.training.exceptions.BookIsbnMismatchException;
import wolox.training.exceptions.BookNotFoundException;
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
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/{ísbn}")
    public Book read(@PathVariable String isbn) {
        try {
            Optional<Book> book = bookRepository.findById(isbn);
            return book.orElse(null);
        } catch(ResponseStatusException e) {
            throw new BookNotFoundException(e);
        }
    }

    @PutMapping("/{isbn}")
    public Book update(@RequestBody Book book, @PathVariable String isbn) {
        if (!book.getIsbn().equals(isbn)) {
            throw new BookIsbnMismatchException();
        }
        exists(isbn);
        return bookRepository.save(book);
    }

    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable String isbn) {
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
