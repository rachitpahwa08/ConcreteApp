package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 21-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Order implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("generationDate")
    @Expose
    private String generationDate;
    @SerializedName("requiredByDate")
    @Expose
    private String requiredByDate;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("requestedBy")
    @Expose
    private String requestedBy;
    @SerializedName("requestedById")
    @Expose
    private String requestedById;
    @SerializedName("supplierId")
    @Expose
    private String supplierId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("customerSite")
    @Expose
    private String customerSite;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDate")
    @Expose
    private String statusDate;
    @SerializedName("statusDesc")
    @Expose
    private String statusDesc;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    public String getRequiredByDate() {
        return requiredByDate;
    }

    public void setRequiredByDate(String requiredByDate) {
        this.requiredByDate = requiredByDate;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedById() {
        return requestedById;
    }

    public void setRequestedById(String requestedById) {
        this.requestedById = requestedById;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(String customerSite) {
        this.customerSite = customerSite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.generationDate);
        dest.writeString(this.requiredByDate);
        dest.writeString(this.quality);
        dest.writeString(this.quantity);
        dest.writeString(this.requestedBy);
        dest.writeString(this.requestedById);
        dest.writeString(this.supplierId);
        dest.writeString(this.price);
        dest.writeString(this.companyName);
        dest.writeString(this.customerSite);
        dest.writeString(this.status);
        dest.writeString(this.statusDate);
        dest.writeString(this.statusDesc);
        dest.writeValue(this.v);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.generationDate = in.readString();
        this.requiredByDate = in.readString();
        this.quality = in.readString();
        this.quantity = in.readString();
        this.requestedBy = in.readString();
        this.requestedById = in.readString();
        this.supplierId = in.readString();
        this.price = in.readString();
        this.companyName = in.readString();
        this.customerSite = in.readString();
        this.status = in.readString();
        this.statusDate = in.readString();
        this.statusDesc = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
