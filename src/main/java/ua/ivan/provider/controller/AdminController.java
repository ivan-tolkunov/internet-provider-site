package ua.ivan.provider.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ivan.provider.model.Status;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserDetailsServiceImpl userDetailsService;

    public AdminController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String getAllUsers(Model model, Authentication authentication) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        return "admin";
    }

    @PostMapping("/banUser")
    public String banUser(@RequestParam(value="id") long id, Model model, Authentication authentication) {
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        User user = userDetailsService.findById(id);
        user.setStatus(Status.BANNED);
        userDetailsService.saveUser(user);
        return "admin";
    }

    @PostMapping("/unbanUser")
    public String unbanUser(@RequestParam(value="id") long id, Model model, Authentication authentication) {
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        User user = userDetailsService.findById(id);
        user.setStatus(Status.ACTIVE);
        userDetailsService.saveUser(user);
        return "admin";
    }

}
