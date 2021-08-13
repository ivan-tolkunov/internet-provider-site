package ua.ivan.provider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.PackageService;
import ua.ivan.provider.service.UserDetailsServiceImpl;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserDetailsServiceImpl userDetailsService;
    private PackageService packageService;

    @Autowired
    public AuthController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService,
                           @Qualifier("packageService") PackageService packageService) {
        this.userDetailsService = userDetailsService;
        this.packageService = packageService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String getMainPage(Model model,
                              Authentication authentication) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        model.addAttribute("isSubscriberInternet",
                packageService.alreadySubscribe(user.getPackages(), "Internet"));
        model.addAttribute("isSubscriberIPTV",
                packageService.alreadySubscribe(user.getPackages(), "IP-TV"));
        model.addAttribute("isSubscriberTelephone",
                packageService.alreadySubscribe(user.getPackages(), "Cellular communication"));
        model.addAttribute("user", user);
        return "main";
    }
}
