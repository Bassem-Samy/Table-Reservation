package com.bassem.tablereservation.network;

import com.bassem.tablereservation.models.Customer;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface CustomersService {
    @GET("customer-list.json")
    Single<List<Customer>> getCustomers();
}
