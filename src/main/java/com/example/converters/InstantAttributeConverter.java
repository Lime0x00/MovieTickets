package com.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant;
import java.sql.Timestamp;

@Converter(autoApply = false)
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        return (instant != null) ? Timestamp.from(instant) : null;
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp dbData) {
        return (dbData != null) ? dbData.toInstant() : null;
    }
}
