package com.example.awbd.controller;

import com.example.awbd.model.Rating;
import com.example.awbd.model.RatingId;
import com.example.awbd.model.Audiotrack;
import com.example.awbd.model.security.User;
import com.example.awbd.repo.RatingRepo;
import com.example.awbd.repo.AudiotrackRepo;
import com.example.awbd.repo.security.UserRepository;
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
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private UserRepository userRepository;

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
            logger.error("Failed to retrieve ratings", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //rating dupa utilizator(id) si piesa(id)
    @GetMapping("/getRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<Rating> getRatingByPersoaneAndAudiotrack(@PathVariable Integer utilizatorId, @PathVariable Long audiotrackId) {
        try {
            Optional<Rating> ratingData = ratingRepo.findByIdPersoanaAndIdAudiotrack(utilizatorId, audiotrackId);

            return ratingData.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception ex) {
            logger.error("Failed to retrieve rating", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //adaugare
    @PostMapping("/addRating")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        try {
            Rating ratingObject = ratingRepo.save(rating);
            return new ResponseEntity<>(ratingObject, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Failed to add rating", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    update (utilizator si piesa)
    @PutMapping("/updateRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<Rating> updateRatingByPersoaneAndAudiotrack(@PathVariable Integer utilizatorId, @PathVariable Long audiotrackId, @RequestBody Rating newRatingData) {
        try {
            Optional<Rating> ratingData = ratingRepo.findByIdPersoanaAndIdAudiotrack(utilizatorId, audiotrackId);

            if (ratingData.isPresent()) {
                Rating updatedRatingData = ratingData.get();
                updatedRatingData.setRating(newRatingData.getRating());

                Rating ratingObject = ratingRepo.save(updatedRatingData);
                return new ResponseEntity<>(ratingObject, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            logger.error("Failed to update rating", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete
    @DeleteMapping("/deleteRatingByUtilizatorAndAudiotrack/{utilizatorId}/{audiotrackId}")
    public ResponseEntity<HttpStatus> deleteRatingByPersoaneAndAudiotrack(@PathVariable Integer utilizatorId, @PathVariable Long audiotrackId) {
        try {
            RatingId ratingId = new RatingId(utilizatorId, audiotrackId);
            ratingRepo.deleteById(ratingId);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Failed to delete rating", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}