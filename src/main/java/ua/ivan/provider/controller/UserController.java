package ua.ivan.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ivan.provider.model.*;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.PackageService;
import ua.ivan.provider.service.UserDetailsServiceImpl;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserDetailsServiceImpl userDetailsService;
    private DonateService donateService;
    private PackageService packageService;

    @Autowired
    public UserController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                          @Qualifier("donateService") DonateService donateService,
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
        model.addAttribute("listOfUserPackages", user.getPackages());
    }

    @GetMapping
    public String getUserPage() {
        return "cabinet";
    }

    @PostMapping
    public String getUserPageGet() {
        return "cabinet";
    }

    @PostMapping("/donate")
    public String donate(Authentication authentication, Donate donate, @RequestParam("sum") Long sum) {
        donateService.requestDonate(donate, sum, userDetailsService.getUserByEmail(authentication.getName()));
        return "redirect:/auth/main";
    }

    @GetMapping("/sort")
    public String sort(Model model, Authentication authentication, @RequestParam("method") String method) {
        model.addAttribute("listOfUserPackages",
                packageService.sortPackages(method, userDetailsService.getUserByEmail(authentication.getName()).getPackages()));
        return "cabinet";
    }

    @GetMapping("/sortMain")
    public String sortMain(Model model, @RequestParam("method") String method) {
        model.addAttribute("listOfSortedPackages",
                packageService.sortPackages(method, packageService.findAll()));
        return "main";
    }

    @PostMapping("/buy")
    public String buy(Long packageId, Long userId) {
        return packageService.buySubscribe(
                userDetailsService.findById(userId),
                packageService.getPackageById(packageId),
                userDetailsService);
    }

    @PostMapping("/unsub")
    public String unsub(Long userId, Long packageId) {
        return packageService.unsubscribe(
                userDetailsService.findById(userId),
                packageService.getPackageById(packageId),
                userDetailsService
        );
    }
}
