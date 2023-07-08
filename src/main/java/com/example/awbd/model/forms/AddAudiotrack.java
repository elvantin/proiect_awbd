package com.example.awbd.model.forms;

import com.example.awbd.model.Artist;
import com.example.awbd.model.AudioAlbum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAudiotrack {
    private Long id_artist;
    private Long id_album;
    private String titlu_piesa;
    private String durata;
}