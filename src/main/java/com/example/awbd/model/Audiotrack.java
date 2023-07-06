package com.example.awbd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "audiotrack")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Audiotrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_artist")
    @JsonIgnore
    private Artist artist;

    @Column(name = "titlu_piesa")
    private String titlu_piesa;

    @ManyToOne
    @JoinColumn(name = "id_album")
    @JsonIgnore
    private AudioAlbum album;

    @Column(name = "durata")
    private String durata;

    @OneToOne(mappedBy = "audiotrack", cascade = CascadeType.ALL)
    @JsonIgnore
    private Lyrics lyrics;
}
