package com.github.gluhov.accountmanagementservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@WritingConverter
@RequiredArgsConstructor
public class MapToJsonbConverter implements Converter<Map<String, Object>, Json> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Json convert(Map<String, Object> source) {
        try {
            return Json.of(objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Map<String, Object> to JSON", e);
        }
    }
}