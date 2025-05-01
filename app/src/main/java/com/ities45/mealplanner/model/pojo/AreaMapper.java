package com.ities45.mealplanner.model.pojo;

import java.util.HashMap;
import java.util.Map;

public class AreaMapper {
    // Sample mapping of country names to their respective country codes (ISO 3166-1 alpha-2)
    private static final Map<String, String> AREA_CODE_MAP = new HashMap<>();

    static {
        AREA_CODE_MAP.put("American", "US");
        AREA_CODE_MAP.put("British", "GB");
        AREA_CODE_MAP.put("Canadian", "CA");
        AREA_CODE_MAP.put("Chinese", "CN");
        AREA_CODE_MAP.put("Croatian", "HR");
        AREA_CODE_MAP.put("Dutch", "NL");
        AREA_CODE_MAP.put("Egyptian", "EG");
        AREA_CODE_MAP.put("Filipino", "PH");
        AREA_CODE_MAP.put("French", "FR");
        AREA_CODE_MAP.put("Greek", "GR");
        AREA_CODE_MAP.put("Indian", "IN");
        AREA_CODE_MAP.put("Irish", "IE");
        AREA_CODE_MAP.put("Italian", "IT");
        AREA_CODE_MAP.put("Jamaican", "JM");
        AREA_CODE_MAP.put("Japanese", "JP");
        AREA_CODE_MAP.put("Kenyan", "KE");
        AREA_CODE_MAP.put("Malaysian", "MY");
        AREA_CODE_MAP.put("Mexican", "MX");
        AREA_CODE_MAP.put("Moroccan", "MA");
        AREA_CODE_MAP.put("Polish", "PL");
        AREA_CODE_MAP.put("Portuguese", "PT");
        AREA_CODE_MAP.put("Russian", "RU");
        AREA_CODE_MAP.put("Spanish", "ES");
        AREA_CODE_MAP.put("Thai", "TH");
        AREA_CODE_MAP.put("Tunisian", "TN");
        AREA_CODE_MAP.put("Turkish", "TR");
        AREA_CODE_MAP.put("Ukrainian", "UA");
        AREA_CODE_MAP.put("Uruguayan", "UY");
        AREA_CODE_MAP.put("Vietnamese", "VN");
    }

    public static String getAreaCode(String areaName) {
        // Convert the area code to lowercase before returning
        return AREA_CODE_MAP.getOrDefault(areaName, "EG").toLowerCase();
    }
}