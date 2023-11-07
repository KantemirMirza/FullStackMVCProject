package fullstackmvcproject.parameter.service;

import fullstackmvcproject.parameter.model.Country;
import fullstackmvcproject.parameter.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> listOfCountry(){
        return countryRepository.findAll();
    }

    public void saveCountry(Country country){
        countryRepository.save(country);
    }

    public Country findCountryById(Integer id) {
        return countryRepository.findById(id).orElse(null);
    }

    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    // SEARCH

    public List<Country> generalSearch(String keyword){
        return countryRepository.generalSearch(keyword);
    }

    //PAGINATION
    public Page<Country> pagination(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return countryRepository.findAll(pageable);
    }
}
