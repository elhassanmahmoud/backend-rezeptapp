package com.rezeptapp.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RezeptController.class)
public class RezeptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RezeptService service;

    @Test
    public void testGetSingleRezept() throws Exception {
        Rezept rezept = new Rezept();
        rezept.setId(1L);
        rezept.setName("Spaghetti");
        rezept.setKategorie("Hauptgericht");
        rezept.setBild("spaghetti.jpg");
        rezept.setBeschreibung("Lecker");
        rezept.setFavorit(false);
        rezept.setZutaten(List.of(Map.of("name", "Nudel", "menge", "200g")));
        rezept.setNaehrwerte(Map.of("kcal", 500));

        when(service.get(1L)).thenReturn(rezept);

        this.mockMvc.perform(get("/rezepte/1"))
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
        rezept1.setBeschreibung("Klassisch italienisch");
        rezept1.setFavorit(true);
        rezept1.setZutaten(List.of(Map.of("name", "Teig", "menge", "1")));
        rezept1.setNaehrwerte(Map.of("kcal", 800));

        Rezept rezept2 = new Rezept();
        rezept2.setId(2L);
        rezept2.setName("Lasagne");
        rezept2.setKategorie("Auflauf");
        rezept2.setBild("lasagne.jpg");
        rezept2.setBeschreibung("Mit Béchamel");
        rezept2.setFavorit(false);
        rezept2.setZutaten(List.of(Map.of("name", "Hack", "menge", "250g")));
        rezept2.setNaehrwerte(Map.of("kcal", 1000));

        when(service.getAll()).thenReturn(List.of(rezept1, rezept2));

        this.mockMvc.perform(get("/rezepte"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Pizza")))
                .andExpect(content().string(containsString("Lasagne")));
    }
}