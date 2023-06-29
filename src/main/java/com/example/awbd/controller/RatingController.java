package com.example.awbd.controller;

import com.example.awbd.model.Rating;
import com.example.awbd.model.RatingId;
import com.example.awbd.model.User;
import com.example.awbd.model.Audiotrack;
import com.example.awbd.repo.RatingRepo;
import com.example.awbd.repo.UserRepo;
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
    private UserRepo userRepo;

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

    @GetMapping("/getRatingByUserAndAudiotrack/{userId}/{audiotrackId}")
    public ResponseEntity<Rating> getRatingByUserAndAudiotrack(@PathVariable Long userId, @PathVariable Long audiotrackId) {
        Optional<Rating> ratingData = ratingRepo.findByUserAndAudiotrack(userId, audiotrackId);

        return ratingData.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addRating")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating ratingObject = ratingRepo.save(rating);
        return new ResponseEntity<>(ratingObject, HttpStatus.OK);
    }

    @PutMapping("/updateRatingByUserAndAudiotrack/{userId}/{audiotrackId}")
    public ResponseEntity<Rating> updateRatingByUserAndAudiotrack(@PathVariable Long userId, @PathVariable Long audiotrackId, @RequestBody Rating newRatingData) {
        Optional<Rating> ratingData = ratingRepo.findByUserAndAudiotrack(userId, audiotrackId);

        if (ratingData.isPresent()) {
            Rating updatedRatingData = ratingData.get();
            updatedRatingData.setRating(newRatingData.getRating());

            Rating ratingObject = ratingRepo.save(updatedRatingData);
            return new ResponseEntity<>(ratingObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteRatingByUserAndAudiotrack/{userId}/{audiotrackId}")
    public ResponseEntity<HttpStatus> deleteRatingByUserAndAudiotrack(@PathVariable Long userId, @PathVariable Long audiotrackId) {
        Optional<User> userData = userRepo.findById(userId);
        Optional<Audiotrack> audiotrackData = audiotrackRepo.findById(audiotrackId);
        RatingId ratingId = new RatingId(userData.orElse(null), audiotrackData.orElse(null));
        ratingRepo.deleteById(ratingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
