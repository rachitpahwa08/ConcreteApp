package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 04-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class User implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private Integer contact;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("__v")
    @Expose
    private Integer v=1;
    @SerializedName("customerSite")
    @Expose
    private List<CustomerSite> customerSite = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<CustomerSite> getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(List<CustomerSite> customerSite) {
        this.customerSite = customerSite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeValue(this.contact);
        dest.writeString(this.pan);
        dest.writeString(this.gstin);
        dest.writeString(this.password);
        dest.writeString(this.userType);
        dest.writeValue(this.v);
        dest.writeList(this.customerSite);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.contact = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pan = in.readString();
        this.gstin = in.readString();
        this.password = in.readString();
        this.userType = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerSite = new ArrayList<CustomerSite>();
        in.readList(this.customerSite, CustomerSite.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
