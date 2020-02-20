package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UserRepository extends CrudRepository<Users,Long> {

    Users findFirstByUsername(String username);

    List<Users> findByBirthdateBetweenAndNameContaining(LocalDate startDate, LocalDate endDate, String namePart);

    @Query("SELECT u FROM Users u WHERE u.birthdate BETWEEN :startDate and :endDate and (:namePart = null or u.name LIKE CONCAT('%',:namePart,'%'))")
    List<Users> findByBirthdateBetweenAndNameContaining2(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("namePart") String namePart
    );

}
