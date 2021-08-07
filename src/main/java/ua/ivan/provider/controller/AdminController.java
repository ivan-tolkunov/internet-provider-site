package ua.ivan.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ivan.provider.model.Status;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserDetailsServiceImpl userDetailsService;
    private DonateService donateService;

    @Autowired
    public AdminController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService, @Qualifier("donateService")DonateService donateService) {
        this.userDetailsService = userDetailsService;
        this.donateService = donateService;
    }

    @GetMapping
    public String getAllUsers(Model model, Authentication authentication) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        return "admin";
    }

    @PostMapping("/banUser")
    public String banUser(@RequestParam(value="id") long id, Model model, Authentication authentication) {
        User user = userDetailsService.findById(id);
        user.setStatus(Status.BANNED);
        userDetailsService.saveUser(user);
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        return "admin";
    }

    @PostMapping("/unbanUser")
    public String unbanUser(@RequestParam(value="id") long id, Model model, Authentication authentication) {
        User user = userDetailsService.findById(id);
        user.setStatus(Status.ACTIVE);
        userDetailsService.saveUser(user);
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        return "admin";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam(value="id_donate") Long donateId, @RequestParam(value="id_user") Long userId, @RequestParam(value="sum") int sum, Model model, Authentication authentication) {
        User user = userDetailsService.findById(userId);
        user.setBalance(user.getBalance() + sum);
        donateService.deleteDonate(donateId);
        userDetailsService.saveUser(user);
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        return "redirect:/admin";
    }

    @PostMapping("/unconfirm")
    public String unconfirm(@RequestParam(value="id_donate") Long donateId, @RequestParam(value="id_user") Long userId, Model model, Authentication authentication) {
        User user = userDetailsService.findById(userId);
        donateService.deleteDonate(donateId);
        userDetailsService.saveUser(user);
        model.addAttribute("user", userDetailsService.getUserByEmail(authentication.getName()));
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        return "redirect:/admin";
    }

}
