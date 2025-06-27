package com.rezeptapp.backend;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rezepte")
public class Rezept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String kategorie;
    private String bild;
    private String beschreibung;
    private Boolean favorit;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = ZutatenConverter.class)
    private List<Zutat> zutaten;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = NaehrwerteConverter.class)
    private Map<String, Object> naehrwerte;

    public Rezept() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getKategorie() { return kategorie; }
    public void setKategorie(String kategorie) { this.kategorie = kategorie; }

    public String getBild() { return bild; }
    public void setBild(String bild) { this.bild = bild; }

    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

    public Boolean getFavorit() { return favorit; }
    public void setFavorit(Boolean favorit) { this.favorit = favorit; }

    public List<Zutat> getZutaten() { return zutaten; }
    public void setZutaten(List<Zutat> zutaten) { this.zutaten = zutaten; }

    public Map<String, Object> getNaehrwerte() { return naehrwerte; }
    public void setNaehrwerte(Map<String, Object> naehrwerte) { this.naehrwerte = naehrwerte; }

    @Override
    public String toString() {
        return "Rezept{id=" + id + ", name='" + name + "', kategorie='" + kategorie + "'}";
    }
}