package com.alatheer.missing.Data.Remote.Model.Authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
