package ua.ivan.provider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(User user) {
        userDetailsService.addNewUser(user, passwordEncoder.encode(user.getPassword()));
        return "redirect:/admin";
    }
}
