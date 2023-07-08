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
@RequestMapping("/lyrics")
public class LyricsController {

    @Autowired
    private LyricsRepo lyricsRepo;

    @GetMapping
    public ResponseEntity<List<Lyrics>> getAllLyrics() {
        try {
            List<Lyrics> lyricsList = new ArrayList<>(lyricsRepo.findAll());

            if (lyricsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(lyricsList);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lyrics> getLyricsById(@PathVariable Long id) {
        Optional<Lyrics> lyricsData = lyricsRepo.findById(id);

        return lyricsData.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Lyrics> addLyrics(@RequestBody Lyrics lyrics) {
        try {
            Lyrics lyricsObject = lyricsRepo.save(lyrics);
            return ResponseEntity.status(HttpStatus.CREATED).body(lyricsObject);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lyrics> updateLyricsById(@PathVariable Long id, @RequestBody Lyrics newLyricsData) {
        Optional<Lyrics> oldLyricsData = lyricsRepo.findById(id);

        if (oldLyricsData.isPresent()) {
            Lyrics updatedLyricsData = oldLyricsData.get();
            updatedLyricsData.setLyricsText(newLyricsData.getLyricsText());
            updatedLyricsData.setAudiotrack(newLyricsData.getAudiotrack());

            Lyrics lyricsObject = lyricsRepo.save(updatedLyricsData);
            return ResponseEntity.ok(lyricsObject);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLyricsById(@PathVariable Long id) {
        try {
            lyricsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
