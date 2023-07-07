package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.model.AudioAlbum;
import com.example.awbd.model.forms.AddAudioAlbum;
import com.example.awbd.repo.ArtistRepo;
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

@Controller
@RequestMapping("/admin-audioalbums")
public class AdminAlbumPageController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @Autowired
    private ArtistRepo artistRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("admin-audioalbums");
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());
        modelAndView.addObject("audioalbums", audioAlbumList);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addAudioAlbumAndSubmit(@ModelAttribute AddAudioAlbum addAudioAlbum) {
        audioAlbumRepo.save(new AudioAlbum(addAudioAlbum.getTitlu_album(), addAudioAlbum.getId_artist(), addAudioAlbum.getAn()));

        ModelAndView modelAndView = new ModelAndView("admin-audioalbums"); //html page
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());
        modelAndView.addObject("audioalbums", audioAlbumList);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteAudioAlbum(@RequestParam Long id) {
        audioAlbumRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-audioalbums");
        return modelAndView;
    }

}
