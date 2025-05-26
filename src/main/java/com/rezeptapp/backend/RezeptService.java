package com.rezeptapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RezeptService {

    @Autowired
    RezeptRepository repo;

    public Rezept save(Rezept rezept) {
        return repo.save(rezept);
    }

    public Rezept get(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Rezept> getAll() {
        List<Rezept> list = new ArrayList<>();
        repo.findAll().forEach(list::add);

        // ✅ Debug-Ausgabe für Render-Log
        System.out.println("🧪 Anzahl Rezepte in Datenbank: " + list.size());

        return list;
    }
}