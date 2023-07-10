package com.example.awbd.controller;

import com.example.awbd.model.Lyrics;
import com.example.awbd.repo.LyricsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // Logger evenimente
    private static final Logger logger = LoggerFactory.getLogger(LyricsController.class);

    @Autowired
    private LyricsRepo lyricsRepo;

    @GetMapping
    public ResponseEntity<List<Lyrics>> getAllLyrics() {
        try {
            logger.info("Retrieving all lyrics");
            List<Lyrics> lyricsList = new ArrayList<>(lyricsRepo.findAll());

            if (lyricsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(lyricsList);

        } catch (Exception ex) {
            logger.error("Failed to retrieve lyrics: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lyrics> getLyricsById(@PathVariable Long id) {
        try {
            logger.info("Retrieving lyrics by ID: {}", id);
            Optional<Lyrics> lyricsData = lyricsRepo.findById(id);

            return lyricsData.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        logger.warn("Lyrics with ID {} not found", id);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    });
        } catch (Exception ex) {
            logger.error("Failed to retrieve lyrics by ID {}: {}", id, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Lyrics> addLyrics(@RequestBody Lyrics lyrics) {
        try {
            logger.info("Adding new lyrics: {}", lyrics);
            Lyrics lyricsObject = lyricsRepo.save(lyrics);
            return ResponseEntity.status(HttpStatus.CREATED).body(lyricsObject);
        } catch (Exception ex) {
            logger.error("Failed to add lyrics: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Lyrics> updateLyricsById(@PathVariable Long id, @RequestBody Lyrics newLyricsData) {
        try {
            logger.info("Updating lyrics with ID {}: {}", id, newLyricsData);
            Optional<Lyrics> oldLyricsData = lyricsRepo.findById(id);

            if (oldLyricsData.isPresent()) {
                Lyrics updatedLyricsData = oldLyricsData.get();
                updatedLyricsData.setLyricsText(newLyricsData.getLyricsText());
                updatedLyricsData.setAudiotrack(newLyricsData.getAudiotrack());

                Lyrics lyricsObject = lyricsRepo.save(updatedLyricsData);
                return ResponseEntity.ok(lyricsObject);
            }

            logger.warn("Lyrics with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Failed to update lyrics with ID {}: {}", id, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLyricsById(@PathVariable Long id) {
        try {
            logger.info("Deleting lyrics with ID: {}", id);
            lyricsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            logger.error("Failed to delete lyrics with ID {}: {}", id, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}