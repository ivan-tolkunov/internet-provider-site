package ua.ivan.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.ivan.provider.model.Donate;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.PackageService;
import ua.ivan.provider.service.UserDetailsServiceImpl;


@Controller
@RequestMapping("/donatePage")
public class DonateController {

    private UserDetailsServiceImpl userDetailsService;
    private DonateService donateService;
    private PackageService packageService;

    @Autowired
    public DonateController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                          @Qualifier("donateService") DonateService donateService,
                          @Qualifier("packageService") PackageService packageService) {
        this.userDetailsService = userDetailsService;
        this.donateService = donateService;
        this.packageService = packageService;
    }

    @GetMapping
    public String donatePage() {
        return "donate";
    }

    @PostMapping("/unbanDonate")
    public String donateForUnban(Donate donate, @RequestParam("sum") Long sum, @RequestParam("email") String email) {
        donateService.requestDonate(donate, sum, userDetailsService.getUserByEmail(email));
        return "redirect:/auth/login";
    }
}
