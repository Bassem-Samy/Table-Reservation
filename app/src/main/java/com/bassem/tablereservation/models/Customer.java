package com.bassem.tablereservation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Bassem Samy on 4/22/2017.
 * class for customer that holds it's fields from the api and to be stored in the db
 */
public class Customer extends RealmObject implements Parcelable {
    // required for realm
    public Customer() {
    }

    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("customerFirstName")
    private String customerFirstName;
    @SerializedName("customerLastName")
    private String customerLastName;

    public Customer(Customer c) {
        this.id = c.id;
        this.customerFirstName = c.customerFirstName;
        this.customerLastName = c.customerLastName;
    }


    protected Customer(Parcel in) {
        id = in.readString();
        customerFirstName = in.readString();
        customerLastName = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    static final String FULLNAME_FORMAT = "%s %s";

    public String getFullName() {
        return String.format(FULLNAME_FORMAT, getCustomerFirstName(), getCustomerLastName());
    }

    public boolean isInFilter(String filterText) {
        if (getFullName().toLowerCase().contains(filterText.toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(customerFirstName);
        parcel.writeString(customerLastName);
    }
}
