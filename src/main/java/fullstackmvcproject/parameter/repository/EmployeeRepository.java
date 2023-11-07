package fullstackmvcproject.parameter.repository;

import fullstackmvcproject.parameter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select e from Employee e where " +
            "concat(e.firstName, e.lastName, e.occupation, e.phone, e.email, e.dateOfBirth, e.country.countryName, e.state.stateName, e.location.city ) like %?1%")
    List<Employee> generalSearch(String keyword);
    //public Employee findByEmployeeName(String employeeName);
}
