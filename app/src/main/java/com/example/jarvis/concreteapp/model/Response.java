package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 31-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Parcelable {
    @SerializedName("validTill")
    @Expose
    private String validTill;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("rmxId")
    @Expose
    private String rmxId;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRmxId() {
        return rmxId;
    }

    public void setRmxId(String rmxId) {
        this.rmxId = rmxId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.validTill);
        dest.writeValue(this.price);
        dest.writeString(this.rmxId);
        dest.writeString(this.id);
    }

    public Response() {
    }

    protected Response(Parcel in) {
        this.validTill = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rmxId = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel source) {
            return new Response(source);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}
