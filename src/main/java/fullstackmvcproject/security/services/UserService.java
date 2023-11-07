package fullstackmvcproject.security.services;

import fullstackmvcproject.security.models.Role;
import fullstackmvcproject.security.models.User;
import fullstackmvcproject.security.models.UserDTO;
import fullstackmvcproject.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void saveUser(UserDTO userDTO) {
        List<Role> userRoles = userDTO.getRoles();
        if (userRoles == null || userRoles.isEmpty()) {
            userRoles = List.of(new Role("USER"));
        }
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhone(),
                passwordEncoder.encode(userDTO.getPassword()), userRoles);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with: " + email));
    }
    @Override
    public List<User> listOfUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    // SEARCH
    public List<User> generalSearch(String keyword){
        return userRepository.generalSearch(keyword);
    }

    //PAGINATION
    public Page<User> pagination(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findAll(pageable);
    }
}
