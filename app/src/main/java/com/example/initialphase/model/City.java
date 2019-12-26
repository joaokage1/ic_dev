package com.example.initialphase.model;

import com.google.firebase.database.ServerValue;

public class City {

    private String name, cityFlag, countryFlag;
    private Object timestamp;

    public City(String name, String cityFlag, String countryFlag) {
        this.name = name;
        this.cityFlag = cityFlag;
        this.countryFlag = countryFlag;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityFlag() {
        return cityFlag;
    }

    public void setCityFlag(String cityFlag) {
        this.cityFlag = cityFlag;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
