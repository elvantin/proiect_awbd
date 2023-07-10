package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.repo.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/show-artists")
public class ArtistsPageController {

    @Autowired
    private ArtistRepo artistRepo;

    // GET request pt "/show-artists"
    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("artists");
        List<Artist> artistList = new ArrayList<>(artistRepo.findAll());
        modelAndView.addObject("artists", artistList);
        return modelAndView;
    }
}