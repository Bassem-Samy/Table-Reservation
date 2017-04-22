package com.bassem.tablereservation.ui.customerslisting;

import com.bassem.tablereservation.models.Customer;
import com.bassem.tablereservation.models.CustomerDataModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class CustomersListingPresenterImpl implements CustomersListingPresenter {

    CustomersListingInteractor mInteractor;
    CustomersListingView mView;
    Disposable mDisposable;

    public CustomersListingPresenterImpl(CustomersListingView view, CustomersListingInteractor interactor) {
        mInteractor = interactor;
        mView = view;
    }

    @Override
    public void getCustomers() {
        mView.showProgress();
        disposeService();
        mDisposable = mInteractor.getCustomers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<CustomerDataModel>>() {
                            @Override
                            public void accept(List<CustomerDataModel> customers) throws Exception {
                                dataUpdated(customers);
                            }
                        }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showServiceError();
                            }
                        });
    }


    @Override
    public void onDestroy() {
        disposeService();
    }

    private void disposeService() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * called when the api returns customers
     *
     * @param customers list returned
     */
    private void dataUpdated(List<CustomerDataModel> customers) {
        mView.hideProgress();

        mView.updateData(customers);
    }

    /**
     * called when an error happens in the api
     */
    private void showServiceError() {
        mView.hideProgress();
        mView.showError();

    }
}
