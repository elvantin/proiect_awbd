package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.model.AudioAlbum;
import com.example.awbd.repo.ArtistRepo;
import com.example.awbd.repo.AudioAlbumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/show-audioalbums")
public class AudioAlbumPageController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @Autowired
    private ArtistRepo artistRepo;

    // GET request "/show-audioalbums"
    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("audioalbums");
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());

        modelAndView.addObject("audioalbums", audioAlbumList);
        return modelAndView;
    }
}