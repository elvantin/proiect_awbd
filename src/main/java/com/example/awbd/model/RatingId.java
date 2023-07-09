package com.example.awbd.model;

import com.example.awbd.model.security.User;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RatingId implements Serializable {

    private Integer idPersoana;
    private Long idAudiotrack;
}
