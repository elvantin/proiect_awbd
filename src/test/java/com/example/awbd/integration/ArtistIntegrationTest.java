package com.example.awbd.integration;

import com.example.awbd.controller.ArtistController;
import com.example.awbd.model.Artist;
import com.example.awbd.repo.ArtistRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig
@WebMvcTest(ArtistController.class)
public class ArtistIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepo artistRepo;

    @Test
    public void testGetAllArtists() throws Exception {
        List<Artist> artistList = new ArrayList<>();
        artistList.add(new Artist( "Artist 1"));
        artistList.add(new Artist( "Artist 2"));

        Mockito.when(artistRepo.findAll()).thenReturn(artistList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getAllArtists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(artistList.size()));
    }
}
