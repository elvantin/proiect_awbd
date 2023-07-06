package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.repo.AudioAlbumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/show-audioAlbums")
public class AudioAlbumPageController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView =new ModelAndView("audioAlbums");
        List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());
        modelAndView.addObject("audioAlbums", audioAlbumList);
        return modelAndView;
    }
}
