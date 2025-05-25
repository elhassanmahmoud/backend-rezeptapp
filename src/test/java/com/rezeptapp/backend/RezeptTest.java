package com.rezeptapp.backend;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RezeptTest {

    @Test
    void testToString() {
        // Eingabedaten
        String name = "Ding";
        String kategorie = "Test";

        // "System under test" aufsetzen
        Rezept rezept = new Rezept();
        rezept.setId(42L);
        rezept.setName(name);
        rezept.setKategorie(kategorie);

        // Erwartetes Ergebnis
        String expected = "Rezept{id=42, name='Ding', kategorie='Test'}";

        // Tatsächliches Ergebnis
        String actual = rezept.toString();

        // Vergleich
        assertEquals(expected, actual);
    }
}