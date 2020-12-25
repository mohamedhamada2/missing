package com.alatheer.missing.Data.Remote.Model.RemoveToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveToken {
    @SerializedName("success")
    @Expose
    private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}