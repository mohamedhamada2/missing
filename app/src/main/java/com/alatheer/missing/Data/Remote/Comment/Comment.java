package com.alatheer.missing.Data.Remote.Comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item_id_fk")
    @Expose
    private Integer itemIdFk;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("item_person_id_fk")
    @Expose
    private Integer itemPersonIdFk;
    @SerializedName("comment_person_id_fk")
    @Expose
    private Integer commentPersonIdFk;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("approved")
    @Expose
    private Integer approved;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("comment_name")
    @Expose
    private String commentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemIdFk() {
        return itemIdFk;
    }

    public void setItemIdFk(Integer itemIdFk) {
        this.itemIdFk = itemIdFk;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getItemPersonIdFk() {
        return itemPersonIdFk;
    }

    public void setItemPersonIdFk(Integer itemPersonIdFk) {
        this.itemPersonIdFk = itemPersonIdFk;
    }

    public Integer getCommentPersonIdFk() {
        return commentPersonIdFk;
    }

    public void setCommentPersonIdFk(Integer commentPersonIdFk) {
        this.commentPersonIdFk = commentPersonIdFk;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }
}
