package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.model.Audiotrack;
import com.example.awbd.model.forms.AddAudioAlbum;
import com.example.awbd.model.forms.AddAudiotrack;
import com.example.awbd.repo.AudioAlbumRepo;
import com.example.awbd.repo.AudiotrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-audiotracks")
public class AdminAudiotrackController {



    @Autowired
    private AudiotrackRepo audiotrackRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("admin-audiotracks");
        List<Audiotrack> audiotrackList = new ArrayList<>(audiotrackRepo.findAll());
        modelAndView.addObject("audiotracks", audiotrackList);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addAudiotrackAndSubmit(@ModelAttribute AddAudiotrack addAudiotrack) {
        audiotrackRepo.save(new Audiotrack(addAudiotrack.getId_artist(), addAudiotrack.getTitlu_piesa(), addAudiotrack.getId_album(), addAudiotrack.getDurata()));

        ModelAndView modelAndView = new ModelAndView("admin-audiotracks"); //html page
        List<Audiotrack> audiotrackList = new ArrayList<>(audiotrackRepo.findAll());
        modelAndView.addObject("audiotracks", audiotrackList); //html page

        modelAndView = new ModelAndView( "redirect:/admin-audiotracks");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateAudiotrack(@ModelAttribute Audiotrack audiotrack) {
        Optional<Audiotrack> optionalAudiotrack = audiotrackRepo.findById(audiotrack.getId());
        if (optionalAudiotrack.isPresent()) {
            Audiotrack existingAudiotrack = optionalAudiotrack.get();
            existingAudiotrack.setId_artist(audiotrack.getId_artist());
            existingAudiotrack.setTitlu_piesa(audiotrack.getTitlu_piesa());
            existingAudiotrack.setId_album(audiotrack.getId_album());
            existingAudiotrack.setDurata(audiotrack.getDurata());
            audiotrackRepo.save(existingAudiotrack);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-audiotracks");
        return modelAndView;
    }
    @PostMapping("/delete")
    public ModelAndView deleteAudiotrack(@RequestParam Long id) {
        audiotrackRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-audiotracks");
        return modelAndView;
    }

}














