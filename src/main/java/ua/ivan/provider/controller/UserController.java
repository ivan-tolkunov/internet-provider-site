package ua.ivan.provider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ivan.provider.model.Donate;
import ua.ivan.provider.model.User;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserDetailsServiceImpl userDetailsService;
    private DonateService donateService;

    @Autowired
    public UserController(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsService, @Qualifier("donateService")DonateService donateService) {
        this.userDetailsService = userDetailsService;
        this.donateService = donateService;
    }

    @GetMapping
    public String getUserPage(Model model, Authentication authentication) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "cabinet";
    }

    @PostMapping("/donate")
    public String donate(Model model, Authentication authentication, Donate donate) {
        User user = userDetailsService.getUserByEmail(authentication.getName());
        donate.setSum(200L);
        donate.setUserId(user);
        donateService.saveDonate(donate);
        model.addAttribute("user", user);
        return "main";
    }
}
