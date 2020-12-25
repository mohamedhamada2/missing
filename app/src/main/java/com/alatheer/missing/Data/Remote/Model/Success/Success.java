package com.alatheer.missing.Data.Remote.Model.Success;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {
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