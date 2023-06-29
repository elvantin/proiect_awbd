package com.example.awbd.controller;

        import com.example.awbd.model.Artist;
        import com.example.awbd.repo.ArtistRepo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    private ArtistRepo artistRepo;

    @GetMapping("/getAllArtists")
    public ResponseEntity<List<Artist>> getAllArtists() {
        try {
            List<Artist> artistList = new ArrayList<>(artistRepo.findAll());

            if (artistList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(artistList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getArtistById/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artistData = artistRepo.findById(id);

        return artistData.map(artist -> new ResponseEntity<>(artist, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addArtist")
    public ResponseEntity<Artist> addArtist(@RequestBody Artist artist) {
        Artist artistObject = artistRepo.save(artist);
        return new ResponseEntity<>(artistObject, HttpStatus.OK);
    }

    @PostMapping("/updateArtistById/{id}")
    public ResponseEntity<Artist> updateArtistById(@PathVariable Long id, @RequestBody Artist newArtistData) {
        Optional<Artist> oldArtistData = artistRepo.findById(id);

        if (oldArtistData.isPresent()) {
            Artist updatedArtistData = oldArtistData.get();
            updatedArtistData.setNume(newArtistData.getNume());

            Artist artistObject = artistRepo.save(updatedArtistData);
            return new ResponseEntity<>(artistObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteArtistById/{id}")
    public ResponseEntity<HttpStatus> deleteArtistById(@PathVariable Long id) {
        artistRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
