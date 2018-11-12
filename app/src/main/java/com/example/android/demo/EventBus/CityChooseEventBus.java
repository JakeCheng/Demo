package com.example.android.demo.EventBus;

/**
 * 城市选择删选
 */

public class CityChooseEventBus {
    String id;
    String type;
    String cityName;
    public CityChooseEventBus(String gameId,String type,String cityName) {
        id = gameId;
        this.type = type;
        this.cityName = cityName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
