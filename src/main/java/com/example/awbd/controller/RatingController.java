package com.example.awbd.controller;

import com.example.awbd.model.Rating;
import com.example.awbd.model.RatingId;
import com.example.awbd.model.Utilizator;
import com.example.awbd.model.Audiotrack;
import com.example.awbd.repo.RatingRepo;
import com.example.awbd.repo.UtilizatorRepo;
import com.example.awbd.repo.AudiotrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RatingController {

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private UtilizatorRepo utilizatorRepo;

    @Autowired
    private AudiotrackRepo audiotrackRepo;

    @GetMapping("/getAllRatings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        try {
            List<Rating> ratingList = new ArrayList<>(ratingRepo.findAll());

            if (ratingList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ratingList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<Rating> getRatingByUtilizatorAndAudiotrack(@PathVariable Long utilizatorId, @PathVariable Long audiotrackId) {
        Optional<Utilizator> utilizatorData = utilizatorRepo.findById(utilizatorId);
        Optional<Audiotrack> audiotrackData = audiotrackRepo.findById(audiotrackId);

        if (utilizatorData.isPresent() && audiotrackData.isPresent()) {
            Optional<Rating> ratingData = ratingRepo.findByUtilizatorAndAudiotrack(utilizatorData.get(), audiotrackData.get());
            return ratingData.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addRating")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating ratingObject = ratingRepo.save(rating);
        return new ResponseEntity<>(ratingObject, HttpStatus.OK);
    }

    @PutMapping("/updateRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<Rating> updateRatingByUtilizatorAndAudiotrack(@PathVariable Long utilizatorId, @PathVariable Long audiotrackId, @RequestBody Rating newRatingData) {
        Optional<Utilizator> utilizatorData = utilizatorRepo.findById(utilizatorId);
        Optional<Audiotrack> audiotrackData = audiotrackRepo.findById(audiotrackId);

        if (utilizatorData.isPresent() && audiotrackData.isPresent()) {
            Optional<Rating> ratingData = ratingRepo.findByUtilizatorAndAudiotrack(utilizatorData.get(), audiotrackData.get());

            if (ratingData.isPresent()) {
                Rating updatedRatingData = ratingData.get();
                updatedRatingData.setRating(newRatingData.getRating());

                Rating ratingObject = ratingRepo.save(updatedRatingData);
                return new ResponseEntity<>(ratingObject, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<HttpStatus> deleteRatingByUtilizatorAndAudiotrack(@PathVariable Long utilizatorId, @PathVariable Long audiotrackId) {
        Optional<Utilizator> utilizatorData = utilizatorRepo.findById(utilizatorId);
        Optional<Audiotrack> audiotrackData = audiotrackRepo.findById(audiotrackId);

        if (utilizatorData.isPresent() && audiotrackData.isPresent()) {
            RatingId ratingId = new RatingId(utilizatorData.get(), audiotrackData.get());
            ratingRepo.deleteById(ratingId);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
