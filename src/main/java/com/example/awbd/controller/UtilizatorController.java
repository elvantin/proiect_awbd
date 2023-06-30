package com.example.awbd.controller;

import com.example.awbd.model.Utilizator;
import com.example.awbd.repo.UtilizatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UtilizatorController {

    @Autowired
    private UtilizatorRepo utilizatorRepo;

    @GetMapping("/getAllUtilizatori")
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        try {
            List<Utilizator> utilizatorList = new ArrayList<>(utilizatorRepo.findAll());

            if (utilizatorList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(utilizatorList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUtilizatorById/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Long id) {
        Optional<Utilizator> utilizatorData = utilizatorRepo.findById(id);

        return utilizatorData.map(utilizator -> new ResponseEntity<>(utilizator, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addUtilizator")
    public ResponseEntity<Utilizator> addUtilizator(@RequestBody Utilizator utilizator) {
        Utilizator utilizatorObject = utilizatorRepo.save(utilizator);
        return new ResponseEntity<>(utilizatorObject, HttpStatus.OK);
    }

    @PostMapping("/updateUtilizatorById/{id}")
    public ResponseEntity<Utilizator> updateUtilizatorById(@PathVariable Long id, @RequestBody Utilizator newUtilizatorData) {
        Optional<Utilizator> oldUtilizatorData = utilizatorRepo.findById(id);

        if (oldUtilizatorData.isPresent()) {
            Utilizator updatedUtilizatorData = oldUtilizatorData.get();
            updatedUtilizatorData.setUtilizator(newUtilizatorData.getUtilizator());
            updatedUtilizatorData.setNume(newUtilizatorData.getNume());
            updatedUtilizatorData.setPrenume(newUtilizatorData.getPrenume());
            updatedUtilizatorData.setEmail(newUtilizatorData.getEmail());
            updatedUtilizatorData.setParola(newUtilizatorData.getParola());

            Utilizator utilizatorObject = utilizatorRepo.save(updatedUtilizatorData);
            return new ResponseEntity<>(utilizatorObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUtilizatorById/{id}")
    public ResponseEntity<HttpStatus> deleteUtilizatorById(@PathVariable Long id) {
        utilizatorRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
