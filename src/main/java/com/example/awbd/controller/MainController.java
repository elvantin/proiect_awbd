package com.example.awbd.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/login")
    public String showLogInForm() {
        return "login";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage() {
        return "accessDenied";
    }

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))){
                return new ModelAndView("main-admin");
            }
        }
        return new ModelAndView("main");
    }
}
