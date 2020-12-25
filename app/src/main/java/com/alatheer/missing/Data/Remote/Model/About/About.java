package com.alatheer.missing.Data.Remote.Model.About;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class About implements Parcelable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    protected About(Parcel in) {
        id = in.readInt();
        logo = in.readString();
        address = in.readString();
        siteName = in.readString();
        notes = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Parcelable.Creator<About> CREATOR = new Parcelable.Creator<About>() {
        @Override
        public About createFromParcel(Parcel in) {
            return new About(in);
        }

        @Override
        public About[] newArray(int size) {
            return new About [size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(logo);
        dest.writeString(address);
        dest.writeString(siteName);
        dest.writeString(notes);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }
}
