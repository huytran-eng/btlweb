package D20PTIT.hoidap.controller;

import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor

public class UserController {
    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public User createForm() {
        return new User();
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        return "user/register";
    }

    @PostMapping("/register")
    public String finishRegister(Model model, @Valid User user) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPosition(User.Position.member);
        System.out.println(user);
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "user/login";
        }

        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String getUserById(Model model, @PathVariable String id) {
        User user = new User();
        user = userRepo.findByUserId(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/user/edit/{id}")
    public String editUserFrm(Model model, @PathVariable String id) {
        User user = userRepo.findByUserId(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(Model model, @Valid User user, @PathVariable String id) {
        User newUser = new User();
        newUser = userRepo.findByUserId(id);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setPosition(User.Position.member);
        newUser.setUsername(user.getUsername());
        userRepo.save(newUser);
        return "redirect:/";
    }

}
