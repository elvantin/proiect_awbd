package com.example.awbd.model.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference // adnotare pt bidirectionalitate
    private List<User> users;

    // Overriding toString() method of String class
    @Override
    public String toString() {
        return "[" + this.id + "] " + this.role;
    }
}
