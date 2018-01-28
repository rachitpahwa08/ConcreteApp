package com.example.jarvis.concreteapp.model;

/**
 * Created by Jarvis on 23-01-2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PO implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("generationDate")
    @Expose
    private String generationDate;
    @SerializedName("validTill")
    @Expose
    private String validTill;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("customerSite")
    @Expose
    private String customerSite;
    @SerializedName("requestedBy")
    @Expose
    private String requestedBy;
    @SerializedName("requestedById")
    @Expose
    private String requestedById;
    @SerializedName("supplierId")
    @Expose
    private String supplierId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("deletedByContractor")
    @Expose
    private Boolean deletedByContractor;
    @SerializedName("confirmedBySupplier")
    @Expose
    private Boolean confirmedBySupplier;

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

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCustomerSite() {
        return customerSite;
    }

    public void setCustomerSite(String customerSite) {
        this.customerSite = customerSite;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getDeletedByContractor() {
        return deletedByContractor;
    }

    public void setDeletedByContractor(Boolean deletedByContractor) {
        this.deletedByContractor = deletedByContractor;
    }

    public Boolean getConfirmedBySupplier() {
        return confirmedBySupplier;
    }

    public void setConfirmedBySupplier(Boolean confirmedBySupplier) {
        this.confirmedBySupplier = confirmedBySupplier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.generationDate);
        dest.writeString(this.validTill);
        dest.writeString(this.quantity);
        dest.writeString(this.quality);
        dest.writeValue(this.price);
        dest.writeString(this.customerSite);
        dest.writeString(this.requestedBy);
        dest.writeString(this.requestedById);
        dest.writeString(this.supplierId);
        dest.writeValue(this.v);
        dest.writeValue(this.deletedByContractor);
        dest.writeValue(this.confirmedBySupplier);
    }

    public PO() {
    }

    protected PO(Parcel in) {
        this.id = in.readString();
        this.generationDate = in.readString();
        this.validTill = in.readString();
        this.quantity = in.readString();
        this.quality = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerSite = in.readString();
        this.requestedBy = in.readString();
        this.requestedById = in.readString();
        this.supplierId = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deletedByContractor = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.confirmedBySupplier = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PO> CREATOR = new Parcelable.Creator<PO>() {
        @Override
        public PO createFromParcel(Parcel source) {
            return new PO(source);
        }

        @Override
        public PO[] newArray(int size) {
            return new PO[size];
        }
    };
}
