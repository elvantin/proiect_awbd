package com.example.awbd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/login")
    public String showLogInForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "logout";
    }
    @GetMapping("/access_denied")
    public String accessDeniedPage() {
        return "accessDenied";
    }

    /**
     * se uita la request:
     * daca utilizatorul este de tip ADMIN, directioneaza catre main-admin.
     * Daca nu -> main.
     */
    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        logger.info("Accessing home page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                logger.info("User is ADMIN");
                return new ModelAndView("main-admin");
            }
        }

        logger.info("User logged in");
        return new ModelAndView("main");
    }
}