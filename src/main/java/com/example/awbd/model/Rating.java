package com.example.awbd.model;

import com.example.awbd.model.security.User;
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

    /*@Id
    @ManyToOne
    @JoinColumn(name = "id_persoane")
    private Persoane persoane;*/

    @Id
    @Column(name = "id_persoana")
    private Integer idPersoana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persoana", referencedColumnName = "id", insertable = false, updatable = false)
    private User persoana;

    @Id
    @Column(name = "id_audiotrack")
    private Long idAudiotrack;

    @ManyToOne
    @JoinColumn(name = "id_audiotrack", referencedColumnName = "id", insertable = false, updatable = false)
    private Audiotrack audiotrack;

    @Column(name = "rating")
    private int rating;
}
