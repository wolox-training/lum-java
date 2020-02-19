package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    Optional<Book> findFirstByAuthor(String author);

    Optional<List<Book>> findByPublisherAndGenreAndYear(String publisher, String genre, String year);

    @Query("SELECT b FROM Book b WHERE (:publisher = null or b.publisher = :publisher) and"
        + "(:genre = null or b.genre = :genre) and (:year = null or year = :year)")
    Optional<List<Book>> findByPublisherAndGenreAndYear2(
        @Param("publisher") String publisher,
        @Param("genre") String genre,
        @Param("year") String year
    );

}
