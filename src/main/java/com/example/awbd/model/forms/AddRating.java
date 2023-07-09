package com.example.awbd.model.forms;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddRating {
    private Integer idUser;
    private Long idAudiotrack;
    private int rating;
}
