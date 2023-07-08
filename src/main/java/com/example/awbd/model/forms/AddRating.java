package com.example.awbd.model.forms;

import com.example.awbd.model.Audiotrack;
import com.example.awbd.model.Utilizator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddRating {
    private Utilizator utilizator;
    private Audiotrack audiotrack;
    private int rating;
}
