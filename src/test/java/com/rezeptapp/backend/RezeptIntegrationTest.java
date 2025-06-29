package com.rezeptapp.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString; // s

@SpringBootTest
@AutoConfigureMockMvc
public class RezeptIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRezeptSpeichernUndAbrufen() throws Exception {
        // Neues Rezept erstellen
        Rezept rezept = new Rezept();
        rezept.setName("Rendertest-Rezept");
        rezept.setKategorie("Test");
        rezept.setBild("bild.png");
        rezept.setBeschreibung("Integrationstest");
        rezept.setFavorit(false);

        // POST (Speichern)
        mockMvc.perform(post("/rezepte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rezept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rendertest-Rezept"));

        // GET (Abrufen)
        mockMvc.perform(get("/rezepte"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Rendertest-Rezept")));
    }
}