package com.bassem.tablereservation.database;

import com.bassem.tablereservation.models.Customer;
import com.bassem.tablereservation.models.Table;

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

    // Customers Methods
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
            mRealm.beginTransaction();
            boolean res = mRealm.where(Customer.class).findAll().deleteAllFromRealm();
            mRealm.commitTransaction();
            return res;
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

    // Tables Methods
    @Override
    public boolean insertOrUpdateTables(List<Table> items) {
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
    public boolean dropTables() {
        try {
            mRealm.beginTransaction();
            boolean res = mRealm.where(Table.class).findAll().deleteAllFromRealm();
            mRealm.commitTransaction();
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Table> getAllTables() {
        try {
            return mRealm.where(Table.class).findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    @Override
    public boolean setTableAvailable(int id, boolean isAvailable) {
        try {
            mRealm.beginTransaction();
            Table table = mRealm.where(Table.class).equalTo("id", id).findFirst();
            table.setAvailable(isAvailable);
            mRealm.insertOrUpdate(table);
            mRealm.commitTransaction();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
