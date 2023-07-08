package com.example.awbd.controller;


import com.example.awbd.model.Lyrics;
import com.example.awbd.model.forms.AddLyrics;
import com.example.awbd.repo.LyricsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
@RequestMapping("/admin-lyrics")
public class AdminLyricsPageController {

        @Autowired
        private LyricsRepo lyricsRepo;
    @RequestMapping({"/{id_audiotrack}"})
    public ModelAndView getHome(@PathVariable Long id_audiotrack) {
        ModelAndView modelAndView =new ModelAndView("admin-lyrics");

        Lyrics existingLyrics = null;
        Optional<Lyrics> optionalLyrics = lyricsRepo.findByIdAudiotrack(id_audiotrack);
        if (optionalLyrics.isPresent()) {
            existingLyrics = optionalLyrics.get();
        }
        else{
            lyricsRepo.save(new Lyrics(id_audiotrack, ""));
            optionalLyrics = lyricsRepo.findByIdAudiotrack(id_audiotrack);
            if (optionalLyrics.isPresent()) {
                existingLyrics = optionalLyrics.get();
            }
        }
        modelAndView.addObject("lyrics", existingLyrics);
        return modelAndView; }

        @PostMapping("/update")
        public ModelAndView updateLyrics(@ModelAttribute Lyrics lyrics) {
            Lyrics existingLyrics;
            Optional<Lyrics> optionalLyrics = lyricsRepo.findById(lyrics.getId());
            if (optionalLyrics.isPresent()) {
                existingLyrics = optionalLyrics.get();
                existingLyrics.setLyricsText(lyrics.getLyricsText());
                lyricsRepo.save(existingLyrics);
            }
            else{
                lyricsRepo.save(new Lyrics(lyrics.getIdAudiotrack(), lyrics.getLyricsText()));
                optionalLyrics = lyricsRepo.findById(lyrics.getId());
                if (optionalLyrics.isPresent()) {
                    lyrics.setIdAudiotrack(optionalLyrics.get().getIdAudiotrack());
                }
            }

            ModelAndView modelAndView = new ModelAndView(String.format("redirect:/admin-lyrics/%s", lyrics.getIdAudiotrack()));
            return modelAndView;
        }
        @PostMapping("/delete")
        public ModelAndView deleteLyrics(@RequestParam Long id) {
            Optional<Lyrics> optionalLyrics = lyricsRepo.findById(id);
            if (optionalLyrics.isPresent()) {
                Lyrics existingLyrics = optionalLyrics.get();
                existingLyrics.setLyricsText("");
                lyricsRepo.save(existingLyrics);
            }
            ModelAndView modelAndView = new ModelAndView("redirect:/admin-audiotracks");
            return modelAndView;
        }

    }

