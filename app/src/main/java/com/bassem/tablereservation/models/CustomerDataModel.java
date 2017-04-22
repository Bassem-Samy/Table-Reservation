package com.bassem.tablereservation.models;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class CustomerDataModel extends Customer {
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
}
