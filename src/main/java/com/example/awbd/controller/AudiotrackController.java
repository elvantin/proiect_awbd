package com.example.awbd.controller;

import com.example.awbd.model.Audiotrack;
import com.example.awbd.repo.AudiotrackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AudiotrackController {

    @Autowired
    private AudiotrackRepo audiotrackRepo;

    @GetMapping("/getAllAudiotracks")
    public ResponseEntity<List<Audiotrack>> getAllAudiotracks() {
        try {
            List<Audiotrack> audiotrackList = new ArrayList<>(audiotrackRepo.findAll());

            if (audiotrackList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(audiotrackList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAudiotrackById/{id}")
    public ResponseEntity<Audiotrack> getAudiotrackById(@PathVariable Long id) {
        Optional<Audiotrack> audiotrackData = audiotrackRepo.findById(id);

        return audiotrackData.map(audiotrack -> new ResponseEntity<>(audiotrack, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addAudiotrack")
    public ResponseEntity<Audiotrack> addAudiotrack(@RequestBody Audiotrack audiotrack) {
        Audiotrack audiotrackObject = audiotrackRepo.save(audiotrack);
        return new ResponseEntity<>(audiotrackObject, HttpStatus.OK);
    }

    @PostMapping("/updateAudiotrackById/{id}")
    public ResponseEntity<Audiotrack> updateAudiotrackById(@PathVariable Long id, @RequestBody Audiotrack newAudiotrackData) {
        Optional<Audiotrack> oldAudiotrackData = audiotrackRepo.findById(id);

        if (oldAudiotrackData.isPresent()) {
            Audiotrack updatedAudiotrackData = oldAudiotrackData.get();
            updatedAudiotrackData.setTitlu_piesa(newAudiotrackData.getTitlu_piesa());
            updatedAudiotrackData.setDurata(newAudiotrackData.getDurata());
            updatedAudiotrackData.setAlbum(newAudiotrackData.getAlbum());

            Audiotrack audiotrackObject = audiotrackRepo.save(updatedAudiotrackData);
            return new ResponseEntity<>(audiotrackObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAudiotrackById/{id}")
    public ResponseEntity<HttpStatus> deleteAudiotrackById(@PathVariable Long id) {
        audiotrackRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
