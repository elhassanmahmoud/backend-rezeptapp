package com.rezeptapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;

@Converter
public class ZutatenConverter implements AttributeConverter<List<Zutat>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Zutat> zutaten) {
        try {
            return mapper.writeValueAsString(zutaten == null ? new ArrayList<>() : zutaten); // ✅ sicherer
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Konvertieren der Zutaten in JSON", e);
        }
    }

    @Override
    public List<Zutat> convertToEntityAttribute(String json) {
        try {
            return (json == null || json.isBlank()) ? new ArrayList<>() :
                    mapper.readValue(json, new TypeReference<List<Zutat>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Zutaten aus JSON", e);
        }
    }
}