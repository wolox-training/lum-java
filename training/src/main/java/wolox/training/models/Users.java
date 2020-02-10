package wolox.training.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Users {

    @Column(nullable = false) private String username;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private LocalDate birthdate;
    @ManyToMany(cascade=CascadeType.ALL) @Column(nullable = false) private List<Book> books;
    @GeneratedValue(strategy = GenerationType.AUTO) @Id private long id;

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
