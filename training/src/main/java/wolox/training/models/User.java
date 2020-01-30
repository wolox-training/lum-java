package wolox.training.models;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {

    @Column(nullable = false) private String username;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private LocalDate birthdate;
    @OneToMany @Column(nullable = false) private List<Book> books;

}
