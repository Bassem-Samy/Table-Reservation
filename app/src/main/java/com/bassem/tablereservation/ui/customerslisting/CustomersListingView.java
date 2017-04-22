package com.bassem.tablereservation.ui.customerslisting;

import com.bassem.tablereservation.models.Customer;

import java.util.List;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface CustomersListingView {

    void updateData(List<Customer> items);
    void showProgress();
    void hideProgress();
    void showError();
}
