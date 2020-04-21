package com.example.missing.Data.Remote.Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("category_id_fk")
    @Expose
    private Integer categoryIdFk;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("govern_id_fk")
    @Expose
    private Integer governIdFk;
    @SerializedName("city_id_fk")
    @Expose
    private Integer cityIdFk;
    @SerializedName("lat")
    @Expose
    private Object lat;
    @SerializedName("long")
    @Expose
    private Object _long;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("img")
    @Expose
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(Integer categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGovernIdFk() {
        return governIdFk;
    }

    public void setGovernIdFk(Integer governIdFk) {
        this.governIdFk = governIdFk;
    }

    public Integer getCityIdFk() {
        return cityIdFk;
    }

    public void setCityIdFk(Integer cityIdFk) {
        this.cityIdFk = cityIdFk;
    }

    public Object getLat() {
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public Object getLong() {
        return _long;
    }

    public void setLong(Object _long) {
        this._long = _long;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
