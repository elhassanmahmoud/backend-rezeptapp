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

        System.out.println(" Anzahl Rezepte in Datenbank: " + list.size());

        for (Rezept r : list) {
            System.out.println("➡ Rezept geladen: " + r.getName() + " (" + r.getId() + ")");
            System.out.println("    Zutaten: " + r.getZutaten());
            System.out.println("    Nährwerte: " + r.getNaehrwerte());
        }

        return list;
    }

    //  HINZUGEFÜGT
    public void delete(Long id) {
        repo.deleteById(id);
    }
}