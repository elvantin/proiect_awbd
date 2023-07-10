package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.repo.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepo artistRepo;

    // GET request artisti.
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        try {
            List<Artist> artistList = new ArrayList<>(artistRepo.findAll());

            if (artistList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(artistList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET request pt artist dupa ID.
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artistData = artistRepo.findById(id);

        return artistData.map(artist -> new ResponseEntity<>(artist, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST request adaugare artist
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Artist> addArtist(@Validated @RequestBody Artist artist, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            artist.setId(null);  // Ensure that the ID is null
            Artist artistObject = artistRepo.save(artist);
            return new ResponseEntity<>(artistObject, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT request update artist dupa ID.
    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtistById(@PathVariable Long id, @RequestBody Artist newArtistData) {
        Optional<Artist> oldArtistData = artistRepo.findById(id);

        if (oldArtistData.isPresent()) {
            Artist updatedArtistData = oldArtistData.get();
            updatedArtistData.setNume(newArtistData.getNume());

            Artist artistObject = artistRepo.save(updatedArtistData);
            return new ResponseEntity<>(artistObject, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE request stergere artist dupa ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArtistById(@PathVariable Long id) {
        try {
            artistRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}