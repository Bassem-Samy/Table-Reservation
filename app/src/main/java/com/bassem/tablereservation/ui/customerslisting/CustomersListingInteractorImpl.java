package com.bassem.tablereservation.ui.customerslisting;

import com.bassem.tablereservation.database.DatabaseHelper;
import com.bassem.tablereservation.models.Customer;
import com.bassem.tablereservation.network.CustomersService;
import com.bassem.tablereservation.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class CustomersListingInteractorImpl implements CustomersListingInteractor {
    Retrofit retrofit;
    CustomersService customersService;
    DatabaseHelper mDatabaseHelper;

    public CustomersListingInteractorImpl(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;

    }

    @Override
    public Single<List<Customer>> getCustomers() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            customersService = retrofit.create(CustomersService.class);
        }
        return customersService.getCustomers();
    }

    @Override
    public boolean insertOrUpdateCustomers(List<Customer> items) {
        return mDatabaseHelper.insertOrUpdateCustomers(items);
    }

    @Override
    public boolean dropCustomers() {
        return mDatabaseHelper.dropCustomers();
    }

    @Override
    public List<Customer> getCustomersFromDatabase() {

        List<Customer> offlineItems = mDatabaseHelper.getAllCustomers();
        ArrayList<Customer> items = new ArrayList<>();
        for (Customer c : offlineItems
                ) {
            items.add(new Customer(c));
        }
        return items;
    }
}
