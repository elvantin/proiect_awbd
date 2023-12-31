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
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    @JsonBackReference // adnotare pt bidirectionalitate
    private List<User> users; // lista utilizatori asociata cu authority

    @Override
    public String toString() {
        return "[" + this.id + "] " + this.role;
    }
}
