package com.bassem.tablereservation.database;

import com.bassem.tablereservation.models.Customer;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class DatabaseHelper implements DatabaseOperations {
    Realm mRealm;

    public DatabaseHelper(Realm realm) {
        mRealm = realm;

    }

    @Override
    public boolean insertOrUpdateCustomers(List<Customer> items) {
        try {
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(items);
            mRealm.commitTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean dropCustomers() {
        try {
            return mRealm.where(Customer.class).findAll().deleteAllFromRealm();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            return mRealm.where(Customer.class).findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }


}
