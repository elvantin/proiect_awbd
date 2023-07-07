package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.model.forms.AddArtist;
import com.example.awbd.repo.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        modelAndView = new ModelAndView("redirect:/admin-artists");
        return modelAndView;
    }
    @PostMapping("/update")
    public ModelAndView updateArtist(@ModelAttribute Artist artist) {
        Optional<Artist> optionalArtist = artistRepo.findById(artist.getId());
        if (optionalArtist.isPresent()) {
            Artist existingArtist = optionalArtist.get();
            existingArtist.setNume(artist.getNume());
            artistRepo.save(existingArtist);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/admin-artists");
        return modelAndView;
    }
    @PostMapping("/delete")
    public ModelAndView deleteArtist(@RequestParam Long id) {
        artistRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-artists");
        return modelAndView;
    }

}
