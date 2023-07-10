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

@SpringBootTest
@AutoConfigureMockMvc
public class AudioAlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AudioAlbumRepo audioAlbumRepo;

    @Test
    public void testCreateUpdateAndDeleteAudioAlbum() throws Exception {
        AudioAlbum audioAlbum = new AudioAlbum("Test Album", 1L, 2023);

        // Create a new audio album
        String response = mockMvc.perform(post("/audioalbums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(audioAlbum)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract the album ID from the response
        AudioAlbum createdAlbum = objectMapper.readValue(response, AudioAlbum.class);
        Long id = createdAlbum.getId();

        // Update the album
        AudioAlbum updatedAlbum = new AudioAlbum("Updated Test Album", 2L, 2024);
        updatedAlbum.setId(id);
        mockMvc.perform(put("/audioalbums/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAlbum)))
                .andExpect(status().isOk());

        // Delete the album
        mockMvc.perform(delete("/audioalbums/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
