package com.rezeptapp.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RezeptController.class)
public class RezeptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private RezeptService rezeptService;

    @BeforeEach
    void setUp() {
        // Optional: Setup vorbereiten
    }

    @Test
    public void testGetSingleRezept() throws Exception {
        Rezept rezept = new Rezept();
        rezept.setId(1L);
        rezept.setName("Spaghetti");
        rezept.setKategorie("Hauptgericht");
        rezept.setBild("spaghetti.jpg");
        rezept.setBeschreibung("Lecker");
        rezept.setFavorit(false);
        rezept.setZutaten(List.of(
                new Zutat("Nudel", "200g", "Teigwaren", "🍝", "10 Minuten kochen")
        ));
        rezept.setNaehrwerte(Map.of("kcal", 500));

        when(rezeptService.get(1L)).thenReturn(rezept);

        mockMvc.perform(get("/rezepte/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Spaghetti")));
    }

    @Test
    public void testGetAllRezepte() throws Exception {
        Rezept rezept1 = new Rezept();
        rezept1.setId(1L);
        rezept1.setName("Pizza");
        rezept1.setKategorie("Italienisch");
        rezept1.setBild("pizza.jpg");
        rezept1.setBeschreibung("Klassisch");
        rezept1.setFavorit(true);
        rezept1.setZutaten(List.of(
                new Zutat("Teig", "1", "Backwaren", "🍕", "Backen")
        ));
        rezept1.setNaehrwerte(Map.of("kcal", 800));

        Rezept rezept2 = new Rezept();
        rezept2.setId(2L);
        rezept2.setName("Lasagne");
        rezept2.setKategorie("Auflauf");
        rezept2.setBild("lasagne.jpg");
        rezept2.setBeschreibung("Mit Käse");
        rezept2.setFavorit(false);
        rezept2.setZutaten(List.of(
                new Zutat("Hack", "250g", "Fleisch", "🥩", "Anbraten")
        ));
        rezept2.setNaehrwerte(Map.of("kcal", 1000));

        when(rezeptService.getAll()).thenReturn(List.of(rezept1, rezept2));

        mockMvc.perform(get("/rezepte"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Pizza")))
                .andExpect(content().string(containsString("Lasagne")));
    }
}