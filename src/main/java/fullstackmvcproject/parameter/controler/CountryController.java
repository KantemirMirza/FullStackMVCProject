package fullstackmvcproject.parameter.controler;

import fullstackmvcproject.parameter.model.Country;
import fullstackmvcproject.parameter.service.CountryService;
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
public class CountryController {

    private final UserDetailsService userDetailsService;
    private final CountryService countryService;

    @GetMapping("/country")
    public String country(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        return"parameter/country/index";
    }

    @GetMapping("/countries")
    public String listOfCountry(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("listOfCountry", countryService.listOfCountry());
        return "parameter/country/listOfCountry";
    }

    @GetMapping("/addCountry")
    public String addCountry(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("createCountry", new Country());
        return"parameter/country/addCountry";
    }

    @PostMapping("/addCountry")
    public String saveCountry(@ModelAttribute("createCountry") @Valid Country country, BindingResult result ){

        if (result.hasErrors()) {
            return"parameter/country/addCountry";
        }
        countryService.saveCountry(country);
        return"redirect:/countries";
    }

    @GetMapping("/countries/{id}/edit")
    public String editCountry(@PathVariable Integer id, Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("editCountry", countryService.findCountryById(id));
        return"parameter/country/editCountry";
    }

    @PostMapping("/countries/{id}/edit")
    public String updateCountry(@PathVariable Integer id, @ModelAttribute("editCountry")
                                  @Valid Country country, BindingResult result){
        if (result.hasErrors()){
            return"parameter/country/editCountry";
        }
        Country count = countryService.findCountryById(id);
        count.setCountryName(country.getCountryName());
        count.setDescription(country.getDescription());
        count.setCapital(country.getCapital());
        count.setCode(country.getCode());
        count.setNationality(country.getNationality());
        count.setContinent(country.getContinent());

        countryService.saveCountry(count);
        return"redirect:/countries";
    }

    @GetMapping("/countries/{id}/delete")
    public String deleteCountry(@PathVariable Integer id){
        Country country = countryService.findCountryById(id);
        countryService.deleteCountry(country);
        return"redirect:/countries";
    }

    @GetMapping("/countries/{id}/info")
    public String getInfoCountry(Model model, @PathVariable Integer id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        Country country = countryService.findCountryById(id);
        model.addAttribute("infoCountry", country);
        return"parameter/country/infoCountry";
    }

    // SEARCH

    @PostMapping("/countries/search")
    public String generalSearch(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("error", "Keyword is required.");
            return "parameter/country/listOfCountry";
        }
        model.addAttribute("listOfCountry", countryService.generalSearch(keyword));
        return "parameter/country/listOfCountry";
    }

    //PAGINATION
    @GetMapping("/countries/page/{pageNumber}")
    public String pagination(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Country> page = countryService.pagination(currentPage);
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        List<Country> countries = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("listOfCountry", countries);

        return "parameter/country/listOfCountry";
    }
}
