package com.bassem.tablereservation.ui.customerslisting;

import com.bassem.tablereservation.models.CustomerDataModel;
import com.bassem.tablereservation.network.CustomersService;
import com.bassem.tablereservation.utils.Constants;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class CustomersListingInteractorImpl implements CustomersListingInteractor {
    Retrofit retrofit;
    CustomersService customersService;

    @Override
    public Single<List<CustomerDataModel>> getCustomers() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            customersService = retrofit.create(CustomersService.class);
        }
        return customersService.getCustomers();
    }
}
