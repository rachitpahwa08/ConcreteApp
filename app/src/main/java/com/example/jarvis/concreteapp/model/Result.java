package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 13-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("orders")
    @Expose
    private List<Object> orders = null;
    @SerializedName("issues")
    @Expose
    private List<Object> issues = null;
    @SerializedName("quotes")
    @Expose
    private List<Object> quotes = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }

    public List<Object> getIssues() {
        return issues;
    }

    public void setIssues(List<Object> issues) {
        this.issues = issues;
    }

    public List<Object> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Object> quotes) {
        this.quotes = quotes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeList(this.orders);
        dest.writeList(this.issues);
        dest.writeList(this.quotes);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.orders = new ArrayList<Object>();
        in.readList(this.orders, Object.class.getClassLoader());
        this.issues = new ArrayList<Object>();
        in.readList(this.issues, Object.class.getClassLoader());
        this.quotes = new ArrayList<Object>();
        in.readList(this.quotes, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
