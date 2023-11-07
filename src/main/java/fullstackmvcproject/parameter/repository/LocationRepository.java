package fullstackmvcproject.parameter.repository;

import fullstackmvcproject.parameter.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "select l from Location l where " +
            "concat(l.state.stateName, l.city, l.address, l.details) like %?1%")
    List<Location> generalSearch(String keyword);

}
