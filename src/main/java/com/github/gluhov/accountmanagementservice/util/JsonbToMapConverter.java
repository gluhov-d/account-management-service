package com.github.gluhov.accountmanagementservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;
import java.util.Map;

@ReadingConverter
public class JsonbToMapConverter implements Converter<Json, Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> convert(Json source) {
        try {
            return objectMapper.readValue(source.asString(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to Map<String, Object>", e);
        }
    }
}