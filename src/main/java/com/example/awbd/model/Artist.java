package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "artist")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(name = "nume")
    private String nume;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<AudioAlbum> audioAlbums;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Audiotrack> audiotracks;
}
