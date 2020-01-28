package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

interface BookRepository {

    @Query("SELECT book FROM Book book WHERE book.author = :name")
    Book findBookByAuthor(@Param("author") String author);

}
