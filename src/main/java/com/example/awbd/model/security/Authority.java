package com.example.awbd.model.security;

import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
