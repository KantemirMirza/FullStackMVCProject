package fullstackmvcproject.parameter.controler;

import fullstackmvcproject.parameter.model.Employee;
import fullstackmvcproject.parameter.service.CountryService;
import fullstackmvcproject.parameter.service.EmployeeService;
import fullstackmvcproject.parameter.service.LocationService;
import fullstackmvcproject.parameter.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
	private final UserDetailsService userDetailsService;
	private final EmployeeService employeeService;
	private final StateService stateService;
	private final CountryService countryService;
	private final LocationService locationService;

	@GetMapping("/employee")
	public String employee(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		return"parameter/employee/index";
	}

	@GetMapping("/employees")
	public String listOfEmployee(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("listOfEmployee", employeeService.listOfEmployee());
		return"/parameter/employee/listOfEmployee";
	}

	@GetMapping("/addEmployee")
	public String addEmployee(Model model,  Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("createEmployee", new Employee());
		model.addAttribute("listOfCountry", countryService.listOfCountry());
		model.addAttribute("listOfState", stateService.listOfState());
		model.addAttribute("listOfLocation", locationService.listOfLocation());
		return"/parameter/employee/addEmployee";
	}

	@PostMapping("/addEmployee")
	public String saveEmployee(@Valid  @ModelAttribute("createEmployee")
								   Employee employee, BindingResult result
							       /*@RequestParam("file") MultipartFile file*/)throws IOException{
		if (result.hasErrors()) {
			return"/parameter/employee/addEmployee";
		}
		String phone = "+90(" + employee.getPhone().substring(0, 3) + ")" +
				employee.getPhone().substring(3, 6) + "-" +
				employee.getPhone().substring(6, 8) + "-" +
				employee.getPhone().substring(8);
		employee.setPhone(phone);
		employeeService.saveEmployee(employee);
		return"redirect:/employees";
	}

	@GetMapping("/employees/{id}/edit")
	public String editEmployee(@PathVariable Integer id, Model model,  Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("editEmployee", employeeService.findEmployeeById(id));
		model.addAttribute("listOfCountry", countryService.listOfCountry());
		model.addAttribute("listOfState", stateService.listOfState());
		model.addAttribute("listOfLocation", locationService.listOfLocation());
		return "/parameter/employee/editEmployee";
	}

	@PostMapping("/employees/{id}/edit")
	public String updateEmployee(@PathVariable Integer id,
								 @ModelAttribute("editEmployee") @Valid Employee employee,
								 BindingResult result) {

		if (result.hasErrors()) {
			return"/parameter/employee/editEmployee";
		}
		Employee emp = employeeService.findEmployeeById(id);// EKLENTİLER GELİR BURAYA
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setOccupation(employee.getOccupation());
		emp.setPhone(employee.getPhone());
		emp.setEmail(employee.getEmail());
		emp.setCountry(employee.getCountry());
		emp.setState(employee.getState());
		emp.setLocation(employee.getLocation());
		emp.setGender(employee.getGender());
		emp.setDateOfBirth(employee.getDateOfBirth());
		emp.setMaritalStatus(employee.getMaritalStatus());
		emp.setPhoto(employee.getPhoto());
		return "redirect:/employees";
	}

	@GetMapping("/employees/{id}/delete")
	public String deleteEmployee(@PathVariable Integer id){
		Employee employee = employeeService.findEmployeeById(id);
		employeeService.deleteEmployee(employee);
		return"redirect:/employees";
	}

	@GetMapping("/employees/{id}/info")
	public String infoEmployee(Model model, @PathVariable Integer id, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("infoEmployee", employeeService.findEmployeeById(id));
		return"/parameter/employee/infoEmployee";
	}

//	@RequestMapping(value="/employees/uploadPhoto", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//		File newFile = new File("D:\\SOLUTIONS\\fleetms\\uploads" + file.getOriginalFilename());
//		newFile.createNewFile();
//		FileOutputStream fout = new FileOutputStream(newFile);
//		fout.write(file.getBytes());
//		fout.close();
//		return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
//	}
//
//	@PostMapping("/employees/uploadPhoto2")
//	public String uploadFile2(@RequestParam("file") MultipartFile file, Principal principal)
//			throws IllegalStateException, IOException {
//		String baseDirectory = "D:\\SOLUTIONS\\fleetms\\src\\main\\resources\\static\\img\\photos\\" ;
//		file.transferTo(new File(baseDirectory + principal.getName() + ".jpg"));
//		return "redirect:/employees";
//	}

	// SEARCH
	@PostMapping("/employees/search")
	public String generalSearch(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			model.addAttribute("error", "Keyword is required.");
			return"/parameter/employee/listOfEmployee";
		}
		model.addAttribute("listOfEmployee", employeeService.generalSearch(keyword));
		return"/parameter/employee/listOfEmployee";
	}

	// PAGINATION
	@GetMapping("/employees/page/{pageNumber}")
	public String pagination(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Employee> page = employeeService.pagination(currentPage);
		int totalPages = page.getTotalPages();
		long totalElements = page.getTotalElements();
		List<Employee> employees = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("listOfEmployee", employees);

		return"/parameter/employee/listOfEmployee";
	}
}
