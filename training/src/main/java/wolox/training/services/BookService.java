package wolox.training.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.book.BookAuthorMismatchException;
import wolox.training.exceptions.book.BookIdMismatchException;
import wolox.training.exceptions.book.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        log.info(String.format("%s was received to POST", book.toString()));
        return bookRepository.save(book);
    }

    public Book readBook(long id) {
        log.info(String.format("%d was received to search Book",id));
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public Book findByAuthor(String author) {
        log.info(String.format("%s (author) was received to search for Book",author));
        return bookRepository.findFirstByAuthor(author).orElseThrow(BookAuthorMismatchException::new);
    }

    public Book updateBook(Book book, long id) {
        log.info(String.format("%s (book) and %d (id) was received to look for Book",book,id));
        if (book.getId() != id) {
            log.error("Book's id mismatches id");
            throw new BookIdMismatchException();
        }
        readBook(id);
        log.info("Book was found");
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        log.info(String.format("%d was found to delete book",id));
        readBook(id);
        log.info("Book was found");
        bookRepository.deleteById(id);
    }
}
