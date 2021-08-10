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
    }

    @GetMapping
    public String getUserPage() {
        return "cabinet";
    }

    @PostMapping("/donate")
    public String donate(Authentication authentication, Donate donate, @RequestParam("sum") Long sum) {
        System.out.println(sum);
        User user = userDetailsService.getUserByEmail(authentication.getName());
        donate.setSum(sum);
        donate.setUserId(user);
        donateService.saveDonate(donate);
        return "main";
    }

    @PostMapping("/buy")
    public String buy(Packages userPackage) {
        User user = userDetailsService.findById(userPackage.getUser().getId());
        user.setBalance(user.getBalance() - userPackage.getPrice());
        packageService.savePackage(userPackage);
//        if (user.getBalance() < 0) {
//            user.setStatus(Status.BANNED);
//            return "redirect:/auth/logout";
//        }
        return "main";
    }
    @PostMapping("/unsub")
    public String unsub(Packages myPackage) {
        User user = new User();
        user.setId(0L);
        user.setFirstName("1");
        user.setLastName("2");
        user.setEmail("0");
        user.setPassword("0");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        user.setBalance(0);
        myPackage.setUser(user);
        packageService.deletePackage(myPackage);
        return "main";
    }
}
