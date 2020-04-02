package com.example.missing.Countries;

import java.util.List;

public class CountryModel {
    String name;
    List<CityModel>cityList;

    public String getName() {
        return name;
    }

    public CountryModel(String name, List<CityModel> cityList) {
        this.name = name;
        this.cityList = cityList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }
}
