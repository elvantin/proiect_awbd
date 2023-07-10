package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.repo.AudioAlbumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/audioalbums")
public class AudioAlbumController {

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    // GET request toate albumele
    @GetMapping
    public ResponseEntity<List<AudioAlbum>> getAllAudioAlbums() {
        try {
            List<AudioAlbum> audioAlbumList = new ArrayList<>(audioAlbumRepo.findAll());

            if (audioAlbumList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(audioAlbumList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET request album dupa id
    @GetMapping("/{id}")
    public ResponseEntity<AudioAlbum> getAudioAlbumById(@PathVariable Long id) {
        Optional<AudioAlbum> audioAlbumData = audioAlbumRepo.findById(id);

        return audioAlbumData.map(audioAlbum -> new ResponseEntity<>(audioAlbum, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST request adaugare album.
    @PostMapping
    public ResponseEntity<AudioAlbum> addAudioAlbum(@Validated @RequestBody AudioAlbum audioAlbum, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            audioAlbum.setArtist(null);
            AudioAlbum audioAlbumObject = audioAlbumRepo.save(audioAlbum);
            return new ResponseEntity<>(audioAlbumObject, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT request update album
    @PutMapping("/{id}")
    public ResponseEntity<AudioAlbum> updateAudioAlbumById(@PathVariable Long id, @RequestBody AudioAlbum newAudioAlbumData) {
        Optional<AudioAlbum> oldAlbumData = audioAlbumRepo.findById(id);

        if (oldAlbumData.isPresent()) {
            AudioAlbum updatedAudioAlbumData = oldAlbumData.get();
            updatedAudioAlbumData.setTitlu_album(newAudioAlbumData.getTitlu_album());
            updatedAudioAlbumData.setArtist(newAudioAlbumData.getArtist());
            updatedAudioAlbumData.setAn(newAudioAlbumData.getAn());

            AudioAlbum audioAlbumObject = audioAlbumRepo.save(updatedAudioAlbumData);
            return new ResponseEntity<>(audioAlbumObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE request stergere
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAudioAlbumById(@PathVariable Long id) {
        try {
            audioAlbumRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}