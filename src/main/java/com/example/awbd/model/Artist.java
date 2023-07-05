package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.validation.annotation.Validated;
import java.util.List;

@Entity
@Table(name = "artist")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Validated
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String nume;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AudioAlbum> audioAlbums;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Audiotrack> audiotracks;

    public Artist(String nume) {
        this.nume = nume;
    }
}


