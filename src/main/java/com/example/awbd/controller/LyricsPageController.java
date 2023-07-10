package com.example.awbd.controller;

import com.example.awbd.model.Lyrics;
import com.example.awbd.repo.LyricsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/show-lyrics")
public class LyricsPageController {

    private static final Logger logger = LoggerFactory.getLogger(LyricsPageController.class);

    @Autowired
    private LyricsRepo lyricsRepo;

    @RequestMapping({"/{id_audiotrack}"})
    public ModelAndView getHome(@PathVariable Long id_audiotrack) {
        logger.info("Getting lyrics for audiotrack with ID: {}", id_audiotrack);

        ModelAndView modelAndView = new ModelAndView("lyrics");

        // versuri dupa audiotrack id
        Lyrics lyrics = lyricsRepo.findByIdAudiotrack(id_audiotrack).orElse(null);

        modelAndView.addObject("lyrics", lyrics);

        logger.info("Retrieved lyrics: {}", lyrics);

        return modelAndView;
    }
}
