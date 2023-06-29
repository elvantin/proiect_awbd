package com.example.awbd.model;

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
public class Audiotrack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_artist")
    private Artist artist;

    @Column(name = "titlu_piesa")
    private String titlu_piesa;

    @ManyToOne
    @JoinColumn(name = "id_album")
    private AudioAlbum album;

    @Column(name = "durata")
    private int durata;

    @OneToOne(mappedBy = "audiotrack", cascade = CascadeType.ALL)
    private Lyrics lyrics;
}
