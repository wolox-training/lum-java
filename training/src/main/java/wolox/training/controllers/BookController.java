package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import wolox.training.models.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book created succesfully"),
        @ApiResponse(code = 500, message = "One or more fields are invalid")
    })
    public Book create(
        @ApiParam(value = "Book to be created") @RequestBody Book book
    ) {
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book returned succesfully"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    @ApiOperation(value = "Giving an id, returns a book", response = Book.class)
    public Book read(
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        return bookService.readBook(id);

    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book updated succesfully"),
        @ApiResponse(code = 400, message = "Book's id mismatches id given"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    @ApiOperation(value = "Giving an id and a book, updates given book", response = Book.class)
    public Book update(
        @ApiParam(value = "Book to update", required = true) @RequestBody Book book,
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        return bookService.updateBook(book,id);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book deleted succesfully"),
        @ApiResponse(code = 400, message = "Book's id mismatches id given"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    @ApiOperation(value = "Giving an id, deletes a book")
    public void delete(
        @ApiParam(value = "Id to find book", required = true) @PathVariable long id
    ) {
        bookService.deleteBook(id);
    }

    @GetMapping
    @ApiOperation(value = "Giving an author, returns a book")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book returned succesfully"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    public Book findByAuthor(
        @ApiParam(value = "Author's name to find book") @RequestParam(name="author", required=false) String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Giving any parameter, returns a book")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book returned succesfully"),
        @ApiResponse(code = 404, message = "Book not found")
    })
    public List<Book> getAll(
        @ApiParam(value = "Genre to find book") @RequestParam(required = false) String genre,
        @ApiParam(value = "Author to find book") @RequestParam(required = false) String author,
        @ApiParam(value = "Image to find book") @RequestParam(required = false) String image,
        @ApiParam(value = "Title to find book") @RequestParam(required = false) String title,
        @ApiParam(value = "Subtitle to find book") @RequestParam(required = false) String subtitle,
        @ApiParam(value = "Publisher to find book") @RequestParam(required = false) String publisher,
        @ApiParam(value = "Year to find book") @RequestParam(required = false) String year,
        @ApiParam(value = "Pages to find book") @RequestParam(required = false) Integer pages,
        @ApiParam(value = "Isbn to find book") @RequestParam(required = false) String isbn,
        @ApiParam(value = "Id to find book") @RequestParam(required = false) Long id
    ) {
        return bookService.getAll(genre, author, image, title, subtitle, publisher, year, pages, isbn, id);
    }
}
