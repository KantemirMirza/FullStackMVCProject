package fullstackmvcproject.security.controllers;

import fullstackmvcproject.security.models.User;
import fullstackmvcproject.security.models.UserDTO;
import fullstackmvcproject.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    // https://github.com/peaksoft-school/airbnb-b5.git

    @GetMapping("/userAccount")
    public String client(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        return"parameter/security/index";
    }

    @GetMapping("/userAccounts")
    public String listOfUsers(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("listOfAccounts", userService.listOfUsers());
        return"parameter/security/listOfUsers";
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("createUser", new UserDTO());
        return "parameter/security/register";
    }

    @PostMapping("/register")
    public String setRegister(@ModelAttribute("createUser") UserDTO userDTO, RedirectAttributes redirectAttributes ){
        userService.saveUser(userDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Registration is Successfully!!!");
        return "redirect:/register";
    }

    @GetMapping("/login")
    public String login(){
        return"parameter/security/login";
    }

    @GetMapping("/userAccounts/{id}/delete")
    public String deleteAccount(@PathVariable Long id){
        User user = userService.findUserById(id);
        userService.deleteUser(user);
        return"redirect:/userAccounts";
    }

    @GetMapping("/userAccounts/{id}/info")
    public String getInfoAccount(Model model, @PathVariable Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);

        User user = userService.findUserById(id);
        model.addAttribute("infoUser", user);
        return"parameter/security/infoUser";
    }

    // SEARCH

    @PostMapping("/userAccounts/search")
    public String generalSearch(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("error", "Keyword is required.");
            return"parameter/security/listOfUsers";
        }
        model.addAttribute("listOfUser", userService.generalSearch(keyword));
        return"parameter/security/listOfUsers";
    }

    //PAGINATION
    @GetMapping("/userAccounts/page/{pageNumber}")
    public String pagination(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<User> page = userService.pagination(currentPage);
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        List<User> users = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("listOfUser", users);

        return"parameter/security/listOfUsers";
    }
}
