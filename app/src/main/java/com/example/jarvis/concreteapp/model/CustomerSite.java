package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 21-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CustomerSite implements Parcelable {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.address);
        dest.writeString(this._long);
        dest.writeString(this.lat);
        dest.writeString(this.name);
        dest.writeString(this.id);
    }

    public CustomerSite() {
    }

    protected CustomerSite(Parcel in) {
        this.address = in.readString();
        this._long = in.readString();
        this.lat = in.readString();
        this.name = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<CustomerSite> CREATOR = new Parcelable.Creator<CustomerSite>() {
        @Override
        public CustomerSite createFromParcel(Parcel source) {
            return new CustomerSite(source);
        }

        @Override
        public CustomerSite[] newArray(int size) {
            return new CustomerSite[size];
        }
    };
}
