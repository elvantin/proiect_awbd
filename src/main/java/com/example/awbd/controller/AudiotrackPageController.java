package com.example.awbd.controller;


import com.example.awbd.model.AudioAlbum;
import com.example.awbd.model.Audiotrack;

import com.example.awbd.model.Rating;
import com.example.awbd.model.RatingId;
import com.example.awbd.model.forms.AddAudiotrack;
import com.example.awbd.model.forms.AddRating;
import com.example.awbd.model.forms.AudiotrackRating;
import com.example.awbd.repo.AudiotrackRepo;
import com.example.awbd.repo.RatingRepo;
import com.example.awbd.repo.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("audiotracks");
        List<Audiotrack> audiotrackList = new ArrayList<>(audiotrackRepo.findAll());

        List<AudiotrackRating> audiotrackRatingList = new ArrayList<>(audiotrackList.stream().map(audiotrack -> new AudiotrackRating(audiotrack)).toList());

        modelAndView.addObject("audiotrackratings", audiotrackRatingList);

        return modelAndView;
    }

    @PostMapping("/rate")
    public ModelAndView rateAudiotrackAndSubmit(@ModelAttribute AddRating addRating) {

        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            var principal = authentication.getPrincipal();
            if(principal instanceof User){
                username = ((User) principal).getUsername();
            }
        }

        if(!username.equals("")) {
            Optional<com.example.awbd.model.security.User> user = userRepository.findByUsername(username);

            int rating;
            try {
                rating = Integer.parseInt(addRating.getRating());
            }
            catch (NumberFormatException e) {
                rating = 0;
            }

            Optional<Rating> existingRating = ratingRepo.findByIdPersoanaAndIdAudiotrack(user.get().getId(), addRating.getIdAudiotrack());
            if (user.isPresent()) {
                if(!existingRating.isPresent() && rating > 0){
                        ratingRepo.save(new Rating(user.get().getId(), addRating.getIdAudiotrack(), rating));
                }
                else{
                    Rating updatedRating = existingRating.get();

                    if(rating == 0)
                    {
                        ratingRepo.deleteById(new RatingId(user.get().getId(), addRating.getIdAudiotrack()));
                    }
                    else {
                        updatedRating.setRating(rating);
                        ratingRepo.save(updatedRating);
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView( "redirect:/show-audiotracks");
        return modelAndView;
    }
}
