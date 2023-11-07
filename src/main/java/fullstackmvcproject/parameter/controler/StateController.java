package fullstackmvcproject.parameter.controler;

import fullstackmvcproject.parameter.model.State;
import fullstackmvcproject.parameter.service.CountryService;
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
public class StateController {
	private final UserDetailsService userDetailsService;
	private final StateService stateService;
	private final CountryService countryService;

	@GetMapping("/state")
	public String state(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		return"parameter/state/index";
	}

	@GetMapping("/states")
	public String listOfState(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("listOfSate", stateService.listOfState());
		return"parameter/state/listOfState";
	}

	@GetMapping("/addState")
	public String addState(Model model, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("createState", new State());
		model.addAttribute("listOfCountry", countryService.listOfCountry());
		return"parameter/state/addState";
	}

	@PostMapping("/addState")
	public String saveState(@ModelAttribute("createState") @Valid State state, BindingResult result){
		if (result.hasErrors()) {
			return"parameter/state/addState";
		}
		stateService.saveState(state);
		return"redirect:/states";
	}

	@GetMapping("/states/{id}/edit")
	public String editState(@PathVariable Integer id, Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("editState", stateService.findStateById(id));
		model.addAttribute("listOfCountry", countryService.listOfCountry());
		return "parameter/state/editState";
	}

	@PostMapping("/states/{id}/edit")
	public String updateState(@PathVariable Integer id, @ModelAttribute("editState")
	                           @Valid State updatedState, BindingResult result) {

		if (result.hasErrors()) {
			return "parameter/state/editState";
		}
		State state = stateService.findStateById(id);
		state.setCountry(updatedState.getCountry());
		state.setStateName(updatedState.getStateName());
		state.setCapital(updatedState.getCapital());
		state.setCode(updatedState.getCode());
		state.setDetails(updatedState.getDetails());
		stateService.saveState(state);
		return "redirect:/states";
	}

	@GetMapping("/states/{id}/delete")
	public String deleteState(@PathVariable Integer id){
		State state = stateService.findStateById(id);
		stateService.deleteState(state);
		return"redirect:/states";
	}

	@GetMapping("/states/{id}/info")
	public String getInfoState(Model model, @PathVariable Integer id, Principal principal){
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("infoState", stateService.findStateById(id));
		return"parameter/state/infoState";
	}

	// SEARCH

	@PostMapping("/states/search")
	public String generalSearch(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			model.addAttribute("error", "Keyword is required.");
			return "parameter/state/listOfState";
		}
		model.addAttribute("listOfState", stateService.generalSearch(keyword));
		return "parameter/state/listOfState";
	}
	//PAGINATION
	@GetMapping("/states/page/{pageNumber}")
	public String pagination(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<State> page = stateService.pagination(currentPage);
		int totalPages = page.getTotalPages();
		long totalElements = page.getTotalElements();
		List<State> states = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("listOfState", states);

		return "parameter/state/listOfState";
	}
}
