package com.bassem.tablereservation.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class Customer {
    @SerializedName("id")
    private String id;
    @SerializedName("customerFirstName")
    private String customerFirstName;
    @SerializedName("customerLastName")
    private String customerLastName;

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
}
