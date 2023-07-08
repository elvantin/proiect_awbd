package com.example.awbd.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RatingId implements Serializable {

    private Persoane persoane;
    private Audiotrack audiotrack;
}
