package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.StringReader;
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
@ApiModel(description = "Users")
@NoArgsConstructor
public class Users {

    @ApiModelProperty(notes = "User's id", required = true) @Column(nullable = false) @Id private String username;
    @ApiModelProperty(notes = "User's name",required = true) @Column(nullable = false) private String name;
    @ApiModelProperty(notes = "User's birthday",required = true) @Column(nullable = false) private LocalDate birthdate;
    @ApiModelProperty(notes = "User's book colection", required = true) @OneToMany @Column(nullable = false) private List<Book> books;

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
