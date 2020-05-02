package com.alatheer.missing.Data.Remote.Items;

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
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("city_title")
    @Expose
    private String cityTitle;
    @SerializedName("govern_title")
    @Expose
    private String governTitle;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getGovernTitle() {
        return governTitle;
    }

    public void setGovernTitle(String governTitle) {
        this.governTitle = governTitle;
    }
}
