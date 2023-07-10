package com.example.awbd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "id_artist")
    private Long id_artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artist", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    @JsonBackReference // adnotare pt bidirectionalitate
    private Artist artist;

    @Column(name = "titlu_piesa")
    private String titlu_piesa;

    @Column(name = "id_album")
    private Long id_album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_album", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private AudioAlbum album;

    @Column(name = "durata")
    private String durata;

    @OneToOne(mappedBy = "audiotrack", cascade = CascadeType.ALL)
    @JsonIgnore
    private Lyrics lyrics;

    @OneToMany(mappedBy = "audiotrack", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    public Audiotrack(Long id_artist, String titlu_piesa, Long id_album, String durata) {
        this.id_artist = id_artist;
        this.titlu_piesa = titlu_piesa;
        this.id_album = id_album;
        this.durata = durata;
    }
}
