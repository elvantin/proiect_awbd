package com.example.awbd.controller;


import com.example.awbd.model.Persoane;
import com.example.awbd.model.forms.AddPersoane;
import com.example.awbd.repo.PersoaneRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-utilizatori")
public class AdminPersoanePageController {

    @Autowired
    private PersoaneRepo persoaneRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("admin-utilizatori");
        List<Persoane> persoaneList = new ArrayList<>(persoaneRepo.findAll());
        modelAndView.addObject("persoaneList", persoaneList);
        return modelAndView;
    }


    @PostMapping("/add")
    public ModelAndView addUtilizatorAndSubmit(@ModelAttribute AddPersoane addPersoane) {
        Persoane persoana = new Persoane();
        persoana.setUzr(addPersoane.getUzr());
        persoana.setNume(addPersoane.getNume());
        persoana.setPrenume(addPersoane.getPrenume());
        persoana.setEmail(addPersoane.getEmail());
        persoana.setParola(addPersoane.getParola());

        persoaneRepo.save(persoana);

        return new ModelAndView("redirect:/admin-utilizatori");
    }

    @PostMapping("/update")
    public ModelAndView updateUtilizator(@ModelAttribute Persoane persoane) {
        Optional<Persoane> optionalPersoane = persoaneRepo.findById(persoane.getId());
        if (optionalPersoane.isPresent()) {
            Persoane existingPersoane = optionalPersoane.get();
            existingPersoane.setUzr(persoane.getUzr());
            existingPersoane.setNume(persoane.getNume());
            existingPersoane.setPrenume(persoane.getPrenume());
            existingPersoane.setEmail(persoane.getEmail());
            existingPersoane.setParola(persoane.getParola());

            persoaneRepo.save(existingPersoane);
        }

        return new ModelAndView("redirect:/admin-utilizatori");
    }

    @PostMapping("/delete")
    public ModelAndView deleteUtilizator(@RequestParam Long id) {
        persoaneRepo.deleteById(id);
        return new ModelAndView("redirect:/admin-utilizatori");
    }
}
