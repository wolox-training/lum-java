package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@ApiModel(description = "Users")
@NoArgsConstructor
public class Users {

    @ApiModelProperty(notes = "User's id", required = true)
    @Column(nullable = false)
    private String username;

    @ApiModelProperty(notes = "User's name",required = true)
    @Column(nullable = false)
    private String name;

    @ApiModelProperty(notes = "User's birthday",required = true)
    @Column(nullable = false)
    private LocalDate birthdate;

    @ApiModelProperty(notes = "User's book colection", required = true)
    @ManyToMany(cascade=CascadeType.ALL)
    @Column(nullable = false)
    private List<Book> books;

    @Id
    @ApiModelProperty(notes = "User's id", required = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
