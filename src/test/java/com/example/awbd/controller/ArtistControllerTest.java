package com.example.awbd.controller;

import com.example.awbd.model.Artist;
import com.example.awbd.repo.ArtistRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Annotation to utilize Mockito's features
@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {

    @InjectMocks  // Sets up the ArtistController object with a mock ArtistRepo
    private ArtistController artistController;

    @Mock  // Creates a mock instance of ArtistRepo
    private ArtistRepo artistRepo;

    private Artist artist1;
    private Artist artist2;

    @BeforeEach  // This method will be called before each test case
    void setup() {
        artist1 = new Artist(1L, "Artist1", null, null);
        artist2 = new Artist(2L, "Artist2", null, null);
    }

    @Test
    void getAllArtists() {
        // Define behavior of artistRepo mock
        when(artistRepo.findAll()).thenReturn(Arrays.asList(artist1, artist2));

        // Call method to be tested
        ResponseEntity<List<Artist>> result = artistController.getAllArtists();

        // Check results
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals("Artist1", result.getBody().get(0).getNume());
        assertEquals("Artist2", result.getBody().get(1).getNume());

        // Verify mock interaction
        verify(artistRepo, times(1)).findAll();
    }

    @Test
    void getArtistById() {
        // Define behavior of artistRepo mock
        when(artistRepo.findById(1L)).thenReturn(Optional.of(artist1));

        // Call method to be tested
        ResponseEntity<Artist> result = artistController.getArtistById(1L);

        // Check results
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Artist1", result.getBody().getNume());

        // Verify mock interaction
        verify(artistRepo, times(1)).findById(1L);
    }

    @Mock
    private BindingResult bindingResult;
    @Test
    void addArtist() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(artistRepo.save(any(Artist.class))).thenReturn(artist1);

        ResponseEntity<Artist> result = artistController.addArtist(artist1, bindingResult);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Artist1", result.getBody().getNume());

        verify(artistRepo, times(1)).save(any(Artist.class));
    }

    @Test
    void updateArtistById() {
        when(artistRepo.findById(1L)).thenReturn(Optional.of(artist1));
        when(artistRepo.save(any(Artist.class))).thenReturn(artist2);

        ResponseEntity<Artist> result = artistController.updateArtistById(1L, artist2);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Artist2", result.getBody().getNume());

        verify(artistRepo, times(1)).findById(1L);
        verify(artistRepo, times(1)).save(any(Artist.class));
    }

    @Test
    void deleteArtistById() {
        doNothing().when(artistRepo).deleteById(anyLong());

        ResponseEntity<HttpStatus> result = artistController.deleteArtistById(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        verify(artistRepo, times(1)).deleteById(anyLong());
    }
}
