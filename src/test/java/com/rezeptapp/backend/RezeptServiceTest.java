package com.rezeptapp.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RezeptServiceTest {

    @Mock
    private RezeptRepository repository;

    @InjectMocks
    private RezeptService service;

    @Test
    @DisplayName("should find a rezept by id")
    void testGet() {
        // Testobjekt vorbereiten
        Rezept rezept = new Rezept();
        rezept.setId(1L);
        rezept.setName("Pizza");

        // Repository-Verhalten simulieren
        when(repository.findById(1L)).thenReturn(Optional.of(rezept));

        // Methode testen
        Rezept result = service.get(1L);

        // Ergebnis überprüfen
        assertEquals("Pizza", result.getName());
    }
}