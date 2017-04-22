package com.bassem.tablereservation.database;

import com.bassem.tablereservation.models.Customer;

import java.util.List;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public interface DatabaseOperations {

    boolean insertOrUpdateCustomers(List<Customer> items);
    boolean dropCustomers();

    List<Customer> getAllCustomers();
}
