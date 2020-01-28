package wolox.training.models;

import com.sun.istack.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
public class Book {

    @Column(nullable = true) private String genre;
    @Column(nullable = false) private String author;
    @Column(nullable = false) private String image;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private String subtitle;
    @Column(nullable = false) private String publisher;
    @Column(nullable = false) private String year;
    @Column(nullable = false) private int pages;
    @Column(nullable = false) private String isbn;

}
