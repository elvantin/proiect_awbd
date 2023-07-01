package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "utilizator")
@NoArgsConstructor(force = true)
@Setter
@Getter
@ToString
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "utilizator")
    private String utilizator;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "email")
    private String email;

    @Column(name = "parola")
    private String parola;

    @OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL)
    private List<Rating> ratings;
    public Utilizator(Long id, String utilizator, String nume, String prenume, String email, String parola) {
        this.id = id;
        this.utilizator = utilizator;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
    }
  }
