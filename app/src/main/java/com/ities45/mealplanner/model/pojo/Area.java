package com.ities45.mealplanner.model.pojo;

public class Area {
    private String strArea;
    private String flagUrl;

    public String getFlagUrl() {
        String areaCode = AreaMapper.getAreaCode(strArea); // Get the country code
        // Flag URL, https://flagcdn.com/{size}/{country_code}.png
        flagUrl = "https://flagcdn.com/64x48/" + areaCode + ".png";
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
