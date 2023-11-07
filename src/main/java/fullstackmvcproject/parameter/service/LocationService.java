package fullstackmvcproject.parameter.service;

import fullstackmvcproject.parameter.model.Location;
import fullstackmvcproject.parameter.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LocationService {
	private final LocationRepository locationRepository;

	public List<Location> listOfLocation() {
		return locationRepository.findAll();
	}

	public void saveLocation(Location location) {
		locationRepository.save(location);
	}

	public Location findLocationById(Integer id) {
		return locationRepository.findById(id).orElse(null);
	}

	public void deleteLocation(Location location) {
		locationRepository.delete(location);
	}

	// SEARCH

	public List<Location> generalSearch(String keyword){
		return locationRepository.generalSearch(keyword);
	}

	//PAGINATION

	public Page<Location> pagination(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return locationRepository.findAll(pageable);
	}
}
