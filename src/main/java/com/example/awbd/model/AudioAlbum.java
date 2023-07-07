package com.example.awbd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importăm adnotarea
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "audioalbum")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Validated
@ToString(exclude = "artist")
public class AudioAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "titlu_album")
    @NotNull
    private String titlu_album;

    @Column(name = "id_artist")
    private Long id_artist;

    private int an;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artist", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    @JsonBackReference // adnotare pt bidirectionalitate
    private Artist artist;

    public AudioAlbum(String titlu_album, Long id_artist, int an) {
        this.titlu_album = titlu_album;
        this.id_artist = id_artist;
        this.an = an;
    }
}

