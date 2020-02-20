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

    @Query("SELECT b FROM Book b WHERE (:genre = null or b.genre = :genre) and "
        + "(:author = null or b.author = :author) and "
        + "(:image = null or  b.image = :image) and "
        + "(:title = null or b.title = :title) and "
        + "(:subtitle = null or b.subtitle = :subtitle) and "
        + "(:publisher = null or b.publisher = :publisher) and "
        + "(:year = null or b.year = :year) and "
        + "(:pages = null or b.pages = :pages) and "
        + "(:isbn = null or b.isbn = :isbn) and "
        + "(:id = null or b.id = :id)")
    Optional<List<Book>> getAll(
        @Param("genre") String genre,
        @Param("author") String author,
        @Param("image") String image,
        @Param("title") String title,
        @Param("subtitle") String subtitle,
        @Param("publisher") String publisher,
        @Param("year") String year,
        @Param("pages") Integer pages,
        @Param("isbn") String isbn,
        @Param("id") Long id
    );
}
