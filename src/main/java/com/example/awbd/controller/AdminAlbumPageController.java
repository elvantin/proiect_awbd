package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.model.forms.AddAudioAlbum;
import com.example.awbd.repo.AudioAlbumRepo;
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
@RequestMapping("/admin-audioalbums")
public class AdminAlbumPageController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    // GET request pt admin-audioalbums
    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("admin-audioalbums");
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());
        modelAndView.addObject("audioalbums", audioAlbumList);
        return modelAndView;
    }

    // POST request pt adaugare album.
    @PostMapping("/add")
    public ModelAndView addAudioAlbumAndSubmit(@ModelAttribute AddAudioAlbum addAudioAlbum) {
        audioAlbumRepo.save(new AudioAlbum(addAudioAlbum.getTitlu_album(), addAudioAlbum.getId_artist(), addAudioAlbum.getAn()));

        ModelAndView modelAndView = new ModelAndView("admin-audioalbums"); //html page
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());
        modelAndView.addObject("audioalbums", audioAlbumList); //html page

        modelAndView = new ModelAndView( "redirect:/admin-audioalbums");
        return modelAndView;
    }

    // POST request pt update album.
    @PostMapping("/update")
    public ModelAndView updateAudioAlbum(@ModelAttribute AudioAlbum audioAlbum) {
        Optional<AudioAlbum> optionalAudioAlbum = audioAlbumRepo.findById(audioAlbum.getId());
        if (optionalAudioAlbum.isPresent()) {
            AudioAlbum existingAudioAlbum = optionalAudioAlbum.get();
            existingAudioAlbum.setTitlu_album(audioAlbum.getTitlu_album());
            existingAudioAlbum.setId_artist(audioAlbum.getId_artist());
            existingAudioAlbum.setAn(audioAlbum.getAn());
            audioAlbumRepo.save(existingAudioAlbum);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-audioalbums");
        return modelAndView;
    }

    // POST request delete
    @PostMapping("/delete")
    public ModelAndView deleteAudioAlbum(@RequestParam Long id) {
        audioAlbumRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-audioalbums");
        return modelAndView;
    }
}