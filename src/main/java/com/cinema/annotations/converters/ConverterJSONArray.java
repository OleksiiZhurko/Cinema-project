package com.cinema.annotations.converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;

/**
 * Converts JSONArray to Database JSON field and Database JSON to JSONArray
 */
@Converter
public final class ConverterJSONArray implements AttributeConverter<JSONArray, String> {

    /**
     * Converts JSONArray to Database JSON (String)
     * @param jsonObject the current converted value
     * @return String field is for Database with JSON type filed
     */
    @Override
    public String convertToDatabaseColumn(JSONArray jsonObject) {
        try {
            return jsonObject.toString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

    /**
     * Converts String (Database JSON value) to JSONArray
     * @param s the current converted value
     * @return JSONArray field
     */
    @Override
    public JSONArray convertToEntityAttribute(String s) {
        try {
            return new JSONArray(s);
        } catch (JSONException ex) {
            return null;
        }
    }

}
