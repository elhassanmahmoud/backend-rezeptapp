package com.rezeptapp.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Map;

@Converter
public class ZutatenConverter implements AttributeConverter<List<Map<String, Object>>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Map<String, Object>> attribute) {
        try {
            return attribute == null ? null : mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert List to JSON", e);
        }
    }

    @Override
    public List<Map<String, Object>> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isBlank()) return null;
            return mapper.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            return null; // alternativ: throw new RuntimeException("Invalid JSON: " + dbData, e);
        }
    }
}