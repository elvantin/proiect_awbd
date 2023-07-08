package com.example.awbd.controller;


import com.example.awbd.model.Lyrics;
import com.example.awbd.repo.LyricsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/show-lyrics")
public class LyricsPageController {

    @Autowired
    private LyricsRepo lyricsRepo;
    @RequestMapping({"/{id_audiotrack}"})
    public ModelAndView getHome(@PathVariable Long id_audiotrack) {
        ModelAndView modelAndView =new ModelAndView("lyrics");
        Lyrics lyrics = lyricsRepo.findByIdAudiotrack(id_audiotrack).orElse(null);
        modelAndView.addObject("lyrics", lyrics);
        return modelAndView;
    }
}
