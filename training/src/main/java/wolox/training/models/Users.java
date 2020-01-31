package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import wolox.training.exceptions.book.BookAlreadyOwnException;
import wolox.training.exceptions.book.BookNotOwnedException;

@Data
@Entity
@NoArgsConstructor
public class Users {

    @Column(nullable = false) @Id private String username;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private LocalDate birthdate;
    @OneToMany @Column(nullable = false) private List<Book> books;

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }
}
