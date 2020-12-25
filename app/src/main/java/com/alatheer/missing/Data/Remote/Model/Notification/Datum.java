package com.alatheer.missing.Data.Remote.Model.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("found_id")
    @Expose
    private Integer foundId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_id_fk")
    @Expose
    private Integer categoryIdFk;
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
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("adress")
    @Expose
    private Object adress;

    public Integer getFoundId() {
        return foundId;
    }

    public void setFoundId(Integer foundId) {
        this.foundId = foundId;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(Integer categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getAdress() {
        return adress;
    }

    public void setAdress(Object adress) {
        this.adress = adress;
    }
}