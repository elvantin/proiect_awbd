/*
package com.example.awbd.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity
@Table(name = "persoane")
@NoArgsConstructor
@Setter
@Getter
@ToString
@Validated
public class Persoane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "uzr")
    private String uzr;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "email")
    private String email;

    @Column(name = "parola")
    private String parola;

    @Column(name = "rol")
    private String rol;


    @OneToMany(mappedBy = "persoane", cascade = CascadeType.ALL)
    private List<Rating> ratings;
    public Persoane(Long id, String uzr, String nume, String prenume, String email, String parola) {
        this.id = id;
        this.uzr = uzr;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
    }
  }
*/

public class PersoaneObsolete {}
