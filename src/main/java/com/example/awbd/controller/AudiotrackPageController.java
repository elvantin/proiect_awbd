package com.example.awbd.controller;


import com.example.awbd.model.Audiotrack;

import com.example.awbd.repo.AudiotrackRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/show-audiotracks")
public class AudiotrackPageController {

    @Autowired
    private AudiotrackRepo audiotrackRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("audiotracks");
        List<Audiotrack> audiotrackList = new ArrayList<>(audiotrackRepo.findAll());

        modelAndView.addObject("audiotracks", audiotrackList);
        return modelAndView;
    }
}
