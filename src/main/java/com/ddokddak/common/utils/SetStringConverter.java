package com.ddokddak.common.utils;

import javax.persistence.AttributeConverter;
import java.util.Set;

public class SetStringConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if(attribute == null) return null;

        return String.join(",", attribute);
    }
    @Override
    public Set<String> convertToEntityAttribute(String dbData){
        if(dbData == null) return null;
        return Set.of(dbData.split(","));
    }
}
