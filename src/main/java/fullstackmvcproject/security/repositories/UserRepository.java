package fullstackmvcproject.security.repositories;

import fullstackmvcproject.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select u from User u where " +
            "concat(u.firstName, u.lastName, u.email, u.phone) like %?1%")
    List<User> generalSearch(String keyword);
}
