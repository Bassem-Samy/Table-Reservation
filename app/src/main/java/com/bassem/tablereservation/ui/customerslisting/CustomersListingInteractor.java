package com.bassem.tablereservation.ui.customerslisting;

import com.bassem.tablereservation.models.CustomerDataModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface CustomersListingInteractor {
    Single<List<CustomerDataModel>> getCustomers();
}
