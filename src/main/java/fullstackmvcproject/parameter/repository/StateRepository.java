package fullstackmvcproject.parameter.repository;

import fullstackmvcproject.parameter.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
//    @Query(value = "select s from State s where " +
//            "concat(s.country.countryName, s.stateName, s.capital, s.code, s.details) like %?1%")
//    List<State> generalSearch(String keyword);

    @Query(value = "SELECT s FROM State s " +
            "WHERE s.country.countryName LIKE %:keyword% " +
            "OR s.stateName LIKE %:keyword% " +
            "OR s.capital LIKE %:keyword% " +
            "OR s.code LIKE %:keyword% " +
            "OR s.details LIKE %:keyword%")
    List<State> generalSearch(@Param("keyword") String keyword);

}
