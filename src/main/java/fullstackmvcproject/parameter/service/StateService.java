package fullstackmvcproject.parameter.service;

import fullstackmvcproject.parameter.model.State;
import fullstackmvcproject.parameter.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
	private final StateRepository stateRepository;

	//Get All States
	public List<State> listOfState() {
		return stateRepository.findAll();
	}

	public State findStateById(Integer id) {
		return stateRepository.findById(id).orElse(null);
	}
	
	//Delete State
	public void deleteState(State state) {
		stateRepository.delete(state);
	}
	
	//Update State
	public void saveState(State state) {
		stateRepository.save(state);
	}

	// SEARCH

	public List<State> generalSearch(String keyword){
		return stateRepository.generalSearch(keyword);
	}

	//PAGINATION
	public Page<State> pagination(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return stateRepository.findAll(pageable);
	}

}
