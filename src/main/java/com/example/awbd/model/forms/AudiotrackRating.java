package com.example.awbd.model.forms;

import lombok.Getter;
import lombok.Setter;
import com.example.awbd.model.Audiotrack;

@Setter
@Getter
public class AudiotrackRating {
    private Audiotrack audiotrack;
    private double rating;

    public AudiotrackRating(Audiotrack audiotrack){
        this.audiotrack = audiotrack;

        if(audiotrack.getRatings() == null){
            this.rating = 0;
        }

        this.rating = audiotrack.getRatings().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0);
    }
}
