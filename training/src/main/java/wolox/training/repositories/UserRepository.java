package wolox.training.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UserRepository extends CrudRepository<Users,String> {

    @Query("SELECT users FROM Users users WHERE users.username = :username")
    Users findUserById(@Param("username") String username);
}
