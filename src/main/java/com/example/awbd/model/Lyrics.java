package com.example.awbd.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(name = "lyrics", columnDefinition = "TEXT")
    private String lyrics;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_audiotrack", referencedColumnName = "id")
    private Audiotrack audiotrack;
}
