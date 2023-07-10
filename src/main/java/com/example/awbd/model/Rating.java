package com.example.awbd.model;

import com.example.awbd.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id_persoana")
    private Integer idPersoana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persoana", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User persoana;

    @Id
    @Column(name = "id_audiotrack")
    private Long idAudiotrack;

    @ManyToOne
    @JoinColumn(name = "id_audiotrack", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Audiotrack audiotrack;

    @Column(name = "rating")
    private int rating;

    public Rating(Integer idPersoana, Long idAudiotrack, int rating) {
        this.idPersoana = idPersoana;
        this.idAudiotrack = idAudiotrack;
        this.rating = rating;
    }
}
