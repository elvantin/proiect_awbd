package com.example.awbd.controller;

import com.example.awbd.model.Persoane;

import com.example.awbd.repo.PersoaneRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersoaneController {

    @Autowired
    private PersoaneRepo persoaneRepo;

    @GetMapping("/getAllUtilizatori")
    public ResponseEntity<List<Persoane>> getAllUtilizatori() {
        try {
            List<Persoane> persoaneList = new ArrayList<>(persoaneRepo.findAll());

            if (persoaneList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(persoaneList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUtilizatorById/{id}")
    public ResponseEntity<Persoane> getUtilizatorById(@PathVariable Long id) {
        Optional<Persoane> persoaneData = persoaneRepo.findById(id);

        return persoaneData.map(persoane -> new ResponseEntity<>(persoane, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addUtilizator")
    public ResponseEntity<Persoane> addUtilizator(@RequestBody Persoane persoana) {
        Persoane persoanaObject = persoaneRepo.save(persoana);
        return new ResponseEntity<>(persoanaObject, HttpStatus.OK);
    }

    @PostMapping("/updateUtilizatorById/{id}")
    public ResponseEntity<Persoane> updateUtilizatorById(@PathVariable Long id, @RequestBody Persoane newUtilizatorData) {
        Optional<Persoane> oldUtilizatorData = persoaneRepo.findById(id);

        if (oldUtilizatorData.isPresent()) {
            Persoane updatedUtilizatorData = oldUtilizatorData.get();
            updatedUtilizatorData.setUzr(newUtilizatorData.getUzr());
            updatedUtilizatorData.setNume(newUtilizatorData.getNume());
            updatedUtilizatorData.setPrenume(newUtilizatorData.getPrenume());
            updatedUtilizatorData.setEmail(newUtilizatorData.getEmail());
            updatedUtilizatorData.setParola(newUtilizatorData.getParola());

            Persoane persoanaObject = persoaneRepo.save(updatedUtilizatorData);
            return new ResponseEntity<>(persoanaObject, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUtilizatorById/{id}")
    public ResponseEntity<HttpStatus> deleteUtilizatorById(@PathVariable Long id) {
        persoaneRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
