package com.example.awbd.model.forms;

import com.example.awbd.model.Audiotrack;
import com.example.awbd.model.Persoane;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddRating {
    private Persoane persoane;
    private Audiotrack audiotrack;
    private int rating;
}
