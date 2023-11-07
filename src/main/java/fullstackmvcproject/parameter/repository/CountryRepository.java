package fullstackmvcproject.parameter.repository;

import fullstackmvcproject.parameter.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "select c from Country c where " +
            "concat(c.description, c.capital, c.code, c.continent, c.nationality) like %?1%")
    List<Country> generalSearch(String keyword);
}
