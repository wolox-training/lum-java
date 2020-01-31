package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import wolox.training.exceptions.BookAlreadyOwnException;

@Data
@Entity
@NoArgsConstructor
public class Users {

    @Column(nullable = false) @Id private String username;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private LocalDate birthdate;
    @OneToMany @Column(nullable = false) private List<Book> books;

    public void setBook(Book book) {
        if (!bookAlreadyExists(book)) {
            books.add(book);
        } else {
            throw new BookAlreadyOwnException();
        }
    }

    private boolean bookAlreadyExists(Book book) {
        boolean isAlreadySet = false;
        for (Book element : books) {
            if (element == book) {
                isAlreadySet = true;
                break;
            }
        }
        return isAlreadySet;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

}
