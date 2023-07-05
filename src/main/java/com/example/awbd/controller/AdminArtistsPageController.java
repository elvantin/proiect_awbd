package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.model.forms.AddArtist;
import com.example.awbd.repo.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin-artists")
public class AdminArtistsPageController {

    @Autowired
    private ArtistRepo artistRepo;
    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView =new ModelAndView("admin-artists"); // The html page name
        List<Artist> artistList = new ArrayList<>(artistRepo.findAll());
        modelAndView.addObject("artists", artistList); // To be used in the html page
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addArtistAndSubmit(@ModelAttribute AddArtist addArtist) {
        artistRepo.save(new Artist(addArtist.getNume()));

        ModelAndView modelAndView =new ModelAndView("admin-artists"); // The html page name
        List<Artist> artistList = new ArrayList<>(artistRepo.findAll());
        modelAndView.addObject("artists", artistList); // To be used in the html page
        return modelAndView;
    }
}
