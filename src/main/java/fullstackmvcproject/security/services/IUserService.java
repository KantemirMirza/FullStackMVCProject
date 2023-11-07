package fullstackmvcproject.security.services;

import fullstackmvcproject.security.models.User;
import fullstackmvcproject.security.models.UserDTO;

import java.util.List;

public interface IUserService {
     void saveUser(UserDTO userDTO);
     User findByEmail(String email);
     List<User> listOfUsers();
     User findUserById(Long id);
     void deleteUser(User user);
     List<User> generalSearch(String keyword);
}
