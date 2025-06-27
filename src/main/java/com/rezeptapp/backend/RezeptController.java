package com.rezeptapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RezeptController {

    @Autowired
    RezeptService service;

    @CrossOrigin
    @PostMapping("/rezepte")
    public Rezept createRezept(@RequestBody Rezept rezept) {
        return service.save(rezept);
    }

    @CrossOrigin
    @GetMapping("/rezepte/{id}")
    public Rezept getRezept(@PathVariable String id) {
        Long rezeptId = Long.parseLong(id);
        return service.get(rezeptId);
    }

    @CrossOrigin
    @GetMapping("/rezepte")
    public List<Rezept> getAllRezepte() {
        System.out.println("📥 /rezepte wurde aufgerufen");
        return service.getAll();
    }

    @CrossOrigin
    @DeleteMapping("/rezepte/{id}")
    public void deleteRezept(@PathVariable Long id) {
        service.delete(id);
    }

    @CrossOrigin
    @PutMapping("/rezepte/{id}")
    public Rezept updateRezept(@PathVariable Long id, @RequestBody Rezept rezept) {
        rezept.setId(id);
        return service.save(rezept);
    }
}