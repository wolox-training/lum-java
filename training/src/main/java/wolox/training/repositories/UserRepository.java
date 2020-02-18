package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UserRepository extends CrudRepository<Users,Long> {

    Users findFirstByUsername(String username);

    List<Users> findByBirthdateBetweenAndNameContaining(LocalDate startDate, LocalDate endDate, String namePart);

}
