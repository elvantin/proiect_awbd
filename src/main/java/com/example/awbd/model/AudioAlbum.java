package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;

import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "audioalbum")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AudioAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(name = "titlu_album")
    @NotNull
    private String titlu_album;

    @Column(name = "id_artist")
    private Long id_artist;

    private int an;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_artist", referencedColumnName = "id", insertable = false, updatable = false)
    private Artist artist;
}