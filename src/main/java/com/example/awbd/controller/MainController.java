package com.example.awbd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
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
        return new ModelAndView("main");
    }
}
