package com.bassem.tablereservation.database;

import com.bassem.tablereservation.models.Customer;
import com.bassem.tablereservation.models.Table;

import java.util.List;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface DatabaseOperations {

    boolean insertOrUpdateCustomers(List<Customer> items);

    boolean dropCustomers();

    List<Customer> getAllCustomers();

    boolean insertOrUpdateTables(List<Table> items);

    boolean dropTables();

    List<Table> getAllTables();

    boolean setTableAvailable(int id,boolean isAvailable);

    boolean insertOrUpdateTableItem(Table item);
}
