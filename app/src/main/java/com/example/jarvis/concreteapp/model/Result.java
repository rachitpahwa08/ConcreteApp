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
    @SerializedName("POs")
    @Expose
    private List<PO> pOs = null;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("issues")
    @Expose
    private List<Issue> issues = null;
    @SerializedName("quotes")
    @Expose
    private List<Quote> quotes = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private Msg msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public List<PO> getPOs() {
        return pOs;
    }
    public void setPOs(List<PO> pOs) {
        this.pOs = pOs;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
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
        dest.writeTypedList(this.quotes);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.orders = new ArrayList<Order>();
        in.readList(this.orders, Order.class.getClassLoader());
        this.issues = new ArrayList<Issue>();
        in.readList(this.issues, Issue.class.getClassLoader());
        this.quotes = in.createTypedArrayList(Quote.CREATOR);
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
    public class Msg {

        @SerializedName("passmsg")
        @Expose
        private String passmsg;

        public String getPassmsg() {
            return passmsg;
        }

        public void setPassmsg(String passmsg) {
            this.passmsg = passmsg;
        }

    }
}
