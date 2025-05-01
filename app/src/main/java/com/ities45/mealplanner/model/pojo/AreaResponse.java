package com.ities45.mealplanner.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<Area> areas;

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
//        for (Area area : areas){
//            String areaName = area.getStrArea();
//            String areaCode = AreaMapper.getAreaCode(areaName); // Get the country code
//            String flagUrl = "https://flagcdn.com/64x48/" + areaCode + ".png"; // Flag URL, https://flagcdn.com/{size}/{country_code}.png
//            area.setFlagUrl(flagUrl);
//        }
    }
}
