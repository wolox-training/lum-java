package wolox.training.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book,String> {

    @Query("SELECT book FROM Book book WHERE book.author = :author")
    Book findBookByAuthor(@Param("author") String author);

}
