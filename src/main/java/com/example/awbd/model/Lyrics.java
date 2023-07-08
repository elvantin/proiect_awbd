package com.example.awbd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "lyrics")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Lyrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(name = "lyrics", columnDefinition = "TEXT")
    private String lyricsText;

    @Column(name = "id_audiotrack")
    private Long idAudiotrack;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audiotrack", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Audiotrack audiotrack;

    public Lyrics (Long id_audiotrack, String lyrics) {
        this.idAudiotrack = id_audiotrack;
        this.lyricsText = lyrics;
    }
}
