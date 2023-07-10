package com.example.awbd.controller;

import com.example.awbd.model.AudioAlbum;
import com.example.awbd.repo.AudioAlbumRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class AudioAlbumControllerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(AudioAlbumControllerIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @Test
    public void testCreateUpdateAndDeleteAudioAlbum() throws Exception {
        AudioAlbum audioAlbum = new AudioAlbum("Test Album", 1L, 2023);

        // creare album nou
        String response = mockMvc.perform(post("/audioalbums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(audioAlbum)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // extragere id din raspuns
        AudioAlbum createdAlbum = objectMapper.readValue(response, AudioAlbum.class);
        Long id = createdAlbum.getId();
        log.info("Created audio album with ID: " + id);

        // updatare album
        AudioAlbum updatedAlbum = new AudioAlbum("Updated Test Album", 2L, 2024);
        updatedAlbum.setId(id);
        mockMvc.perform(put("/audioalbums/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAlbum)))
                .andExpect(status().isOk());
        log.info("Updated audio album with ID: " + id);

        // stergere album
        mockMvc.perform(delete("/audioalbums/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Deleted audio album with ID: " + id);
    }
}