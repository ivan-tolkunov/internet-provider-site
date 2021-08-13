package ua.ivan.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.PackageService;
import ua.ivan.provider.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserDetailsServiceImpl userDetailsService;
    private DonateService donateService;
    private PackageService packageService;

    @Autowired
    public AdminController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                           @Qualifier("donateService")DonateService donateService,
                           @Qualifier("packageService") PackageService packageService) {
        this.userDetailsService = userDetailsService;
        this.donateService = donateService;
        this.packageService = packageService;
    }

    @ModelAttribute
    public void addAttributes(Model model,
                              Authentication authentication) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("listOfUsers", userDetailsService.findAll());
        model.addAttribute("listOfDonates", donateService.findAll());
        model.addAttribute("listOfPackages", packageService.findAll());
    }

    @GetMapping
    public String getAllUsers() {
        return "admin";
    }

    @PostMapping
    public String getAdminPage() {
        return "admin";
    }

    @PostMapping("/banUser")
    public String banUser(@RequestParam(value="id") long id) {
        userDetailsService.banUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/unbanUser")
    public String unbanUser(@RequestParam(value="id") long id) {
        userDetailsService.unbanUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam(value="id_donate") Long donateId,
                          @RequestParam(value="id_user") Long userId,
                          @RequestParam(value="sum") int sum) {
        userDetailsService.confirmDonate(sum, userId, donateService.findById(donateId));
        donateService.deleteDonate(donateId);
        return "redirect:/admin";
    }

    @PostMapping("/unconfirm")
    public String unconfirm(@RequestParam(value="id_donate") Long donateId) {
        donateService.deleteDonate(donateId);
        return "redirect:/admin";
    }

}
