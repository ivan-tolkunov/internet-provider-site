package ua.ivan.provider.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/changeLanguage")
public class LanguageController {

    @GetMapping
    public RedirectView  changeLanguage(HttpServletRequest request) {
        return  new RedirectView(request.getHeader("referer"));
    }

}
