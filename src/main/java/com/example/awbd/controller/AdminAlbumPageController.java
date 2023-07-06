package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.repo.AudioAlbumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminAlbumPageController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @GetMapping("/admin/audioAlbums")
    public String getAllAudioAlbums(Model model) {
        List<AudioAlbum> audioAlbumList = audioAlbumRepo.findAll();
        model.addAttribute("audioAlbums", audioAlbumList);
        model.addAttribute("newAudioAlbum", new AudioAlbum());
        return "admin-audioalbums";
    }

    @PostMapping("/admin/audioAlbums")
    public String addAudioAlbum(@ModelAttribute AudioAlbum newAudioAlbum) {
        audioAlbumRepo.save(newAudioAlbum);
        return "redirect:/admin/audioAlbums";
    }

    @PostMapping("/admin/audioAlbums/delete")
    public String deleteAudioAlbum(@RequestParam Long id) {
        audioAlbumRepo.deleteById(id);
        return "redirect:/admin/audioAlbums";
    }
}
