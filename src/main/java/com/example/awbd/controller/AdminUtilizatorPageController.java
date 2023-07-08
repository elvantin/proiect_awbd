package com.example.awbd.controller;

import com.example.awbd.model.Utilizator;
import com.example.awbd.model.forms.AddUtilizator;
import com.example.awbd.repo.UtilizatorRepo;
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
public class AdminUtilizatorPageController {

    @Autowired
    private UtilizatorRepo utilizatorRepo;

    @RequestMapping({"", "/"})
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("admin-utilizatori");
        List<Utilizator> utilizatorList = new ArrayList<>(utilizatorRepo.findAll());
        modelAndView.addObject("utilizatorList", utilizatorList);
        return modelAndView;
    }


    @PostMapping("/add")
    public ModelAndView addUtilizatorAndSubmit(@ModelAttribute AddUtilizator addUtilizator) {
        Utilizator utilizator = new Utilizator();
        utilizator.setUtilizator(addUtilizator.getUtilizator());
        utilizator.setNume(addUtilizator.getNume());
        utilizator.setPrenume(addUtilizator.getPrenume());
        utilizator.setEmail(addUtilizator.getEmail());
        utilizator.setParola(addUtilizator.getParola());

        utilizatorRepo.save(utilizator);

        return new ModelAndView("redirect:/admin-utilizatori");
    }

    @PostMapping("/update")
    public ModelAndView updateUtilizator(@ModelAttribute Utilizator utilizator) {
        Optional<Utilizator> optionalUtilizator = utilizatorRepo.findById(utilizator.getId());
        if (optionalUtilizator.isPresent()) {
            Utilizator existingUtilizator = optionalUtilizator.get();
            existingUtilizator.setUtilizator(utilizator.getUtilizator());
            existingUtilizator.setNume(utilizator.getNume());
            existingUtilizator.setPrenume(utilizator.getPrenume());
            existingUtilizator.setEmail(utilizator.getEmail());
            existingUtilizator.setParola(utilizator.getParola());

            utilizatorRepo.save(existingUtilizator);
        }

        return new ModelAndView("redirect:/admin-utilizatori");
    }

    @PostMapping("/delete")
    public ModelAndView deleteUtilizator(@RequestParam Long id) {
        utilizatorRepo.deleteById(id);
        return new ModelAndView("redirect:/admin-utilizatori");
    }
}
