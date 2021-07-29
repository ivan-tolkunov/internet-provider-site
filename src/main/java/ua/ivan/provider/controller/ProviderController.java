package ua.ivan.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProviderController {


    @GetMapping("/")
    public String indexPage(@RequestParam(defaultValue = "en") String language) {
        return "index";
    }

    @GetMapping("/registration")
    public String regPage(@RequestParam(defaultValue = "en") String language) {
        return "registration";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(defaultValue = "en") String language) {
        return "main";
    }

}
