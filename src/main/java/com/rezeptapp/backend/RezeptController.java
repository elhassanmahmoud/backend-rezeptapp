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
        return service.getAll();
    }
}