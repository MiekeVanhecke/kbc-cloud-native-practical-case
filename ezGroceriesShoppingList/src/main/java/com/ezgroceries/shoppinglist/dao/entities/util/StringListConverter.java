package com.ezgroceries.shoppinglist.dao.entities.util;

import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> set) {
        if (!CollectionUtils.isEmpty(set)) {
            return "," + String.join(",", set) + ",";
        } else {
            return null;
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if (joined != null) {
            String values = joined.substring(1, joined.length() - 1); //Removes leading and trailing commas
            return new ArrayList<>(Arrays.asList(values.split(",")));
        }
        return new ArrayList<>();
    }
}
