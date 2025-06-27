package com.rezeptapp.backend;

public class Zutat {
    private String name;
    private String menge;
    private String kategorie; // z. B. "Italienisch", "Asiatisch"
    private String symbol; // z. B. 🍅 oder 🍜
    private String kochanleitung;

    // Konstruktor
    public Zutat() {}

    public Zutat(String name, String menge, String kategorie, String symbol, String kochanleitung) {
        this.name = name;
        this.menge = menge;
        this.kategorie = kategorie;
        this.symbol = symbol;
        this.kochanleitung = kochanleitung;
    }

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMenge() { return menge; }
    public void setMenge(String menge) { this.menge = menge; }

    public String getKategorie() { return kategorie; }
    public void setKategorie(String kategorie) { this.kategorie = kategorie; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getKochanleitung() { return kochanleitung; }
    public void setKochanleitung(String kochanleitung) { this.kochanleitung = kochanleitung; }
}
