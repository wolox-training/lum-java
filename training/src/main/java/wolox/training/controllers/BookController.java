package wolox.training.controllers;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public Book create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public Book read(@PathVariable long id) {
        return bookService.readBook(id);
    }

    @GetMapping
    public Book findByAuthor(@RequestParam(name="author", required=false) String author) {
        return bookService.findByAuthor(author);
    }

    @PutMapping("/{id}")
    public Book update(@RequestBody Book book, @PathVariable long id) {
        return bookService.updateBook(book,id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}
