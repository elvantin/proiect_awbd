package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@IdClass(RatingId.class)
public class Rating {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_utilizator")
    private Utilizator utilizator;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_audiotrack")
    private Audiotrack audiotrack;

    @Column(name = "rating")
    private int rating;
}
