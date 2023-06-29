package com.example.awbd.controller;

import com.example.awbd.model.Lyrics;
import com.example.awbd.repo.LyricsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LyricsController {

    @Autowired
    private LyricsRepo lyricsRepo;

    @GetMapping("/getAllLyrics")
    public ResponseEntity<List<Lyrics>> getAllLyrics() {
        try {
            List<Lyrics> lyricsList = new ArrayList<>(lyricsRepo.findAll());

            if (lyricsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lyricsList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLyricsById/{id}")
    public ResponseEntity<Lyrics> getLyricsById(@PathVariable Long id) {
        Optional<Lyrics> lyricsData = lyricsRepo.findById(id);

        return lyricsData.map(lyrics -> new ResponseEntity<>(lyrics, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addLyrics")
    public ResponseEntity<Lyrics> addLyrics(@RequestBody Lyrics lyrics) {
        Lyrics lyricsObject = lyricsRepo.save(lyrics);
        return new ResponseEntity<>(lyricsObject, HttpStatus.OK);
    }

    @PostMapping("/updateLyricsById/{id}")
    public ResponseEntity<Lyrics> updateLyricsById(@PathVariable Long id, @RequestBody Lyrics newLyricsData) {
        Optional<Lyrics> oldLyricsData = lyricsRepo.findById(id);

        if (oldLyricsData.isPresent()) {
            Lyrics updatedLyricsData = oldLyricsData.get();
            updatedLyricsData.setLyrics(newLyricsData.getLyrics());
            updatedLyricsData.setAudiotrack(newLyricsData.getAudiotrack());

            Lyrics lyricsObject = lyricsRepo.save(updatedLyricsData);
            return new ResponseEntity<>(lyricsObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteLyricsById/{id}")
    public ResponseEntity<HttpStatus> deleteLyricsById(@PathVariable Long id) {
        lyricsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}