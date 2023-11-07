package fullstackmvcproject.parameter.service;

import fullstackmvcproject.parameter.model.Employee;
import fullstackmvcproject.parameter.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	
	//Get All Employees
	public List<Employee> listOfEmployee() {
		return employeeRepository.findAll();
	}
	
	//Get Employee By
	public Employee findEmployeeById(int id) {
		return employeeRepository.findById(id).orElse(null);
	}	
	
	//Delete Employee
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
	}
	
	//Update Employee
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	// SEARCH
	public List<Employee> generalSearch(String keyword){
		return employeeRepository.generalSearch(keyword);
	}

	//PAGINATION
	public Page<Employee> pagination(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return employeeRepository.findAll(pageable);
	}
}
