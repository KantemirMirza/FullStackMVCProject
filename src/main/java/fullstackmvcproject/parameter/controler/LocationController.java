package fullstackmvcproject.parameter.controler;

import fullstackmvcproject.parameter.model.Location;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationController {
	private final UserDetailsService userDetailsService;
	private final LocationService locationService;
	private final StateService stateService;

	@GetMapping("/location")
	public String location(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		return"parameter/location/index";
	}

	@GetMapping("/locations")
	public String listOfLocation(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("listOfLocation", locationService.listOfLocation());
		return"parameter/location/listOfLocation";
	}

	@GetMapping("/addLocation")
	public String addLocation(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("createLocation", new Location());
		model.addAttribute("listOfState", stateService.listOfState());
		return"parameter/location/addLocation";
	}

	@PostMapping("/addLocation")
	public String saveLocation(@ModelAttribute("createLocation") @Valid Location location, BindingResult result){
		if (result.hasErrors()) {
			return"parameter/location/addLocation";
		}
		locationService.saveLocation(location);
		return"redirect:/locations";
	}

	@GetMapping("/locations/{id}/edit")
	public String editLocation(@PathVariable Integer id, Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("editLocation", locationService.findLocationById(id));
		model.addAttribute("listOfState", stateService.listOfState());
		return "parameter/location/editLocation";
	}

	@PostMapping("/locations/{id}/edit")
	public String updateLocation(@PathVariable Integer id, @ModelAttribute("editLocation")@Valid Location loc, BindingResult result) {

		if (result.hasErrors()) {
			return "parameter/location/editLocation";
		}

		Location location = locationService.findLocationById(id);
		location.setState(loc.getState());
		location.setCity(loc.getCity());
		location.setAddress(loc.getAddress());
		location.setDetails(loc.getDetails());
		locationService.saveLocation(location);
		return "redirect:/locations";
	}

	@GetMapping("/locations/{id}/delete")
	public String deleteLocation(@PathVariable Integer id){
		Location location = locationService.findLocationById(id);
		locationService.deleteLocation(location);
		return"redirect:/locations";
	}

	@GetMapping("/locations/{id}/info")
	public String infoLocation(Model model, @PathVariable Integer id, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("infoLocation", locationService.findLocationById(id));
		return"parameter/location/infoLocation";
	}

	// SEARCH

	@PostMapping("/locations/search")
	public String generalSearch(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			model.addAttribute("error", "Keyword is required.");
			return"parameter/location/listOfLocation";
		}
		model.addAttribute("listOfLocation", locationService.generalSearch(keyword));
		return"parameter/location/listOfLocation";
	}

	//PAGINATION

	@GetMapping("/locations/page/{pageNumber}")
	public String pagination(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Location> page = locationService.pagination(currentPage);
		int totalPages = page.getTotalPages();
		long totalElements = page.getTotalElements();
		List<Location> locations = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("listOfLocation", locations);

		return"parameter/location/listOfLocation";
	}
}
