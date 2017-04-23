package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.models.Table;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public class TablesPresenterImpl implements TablesPresenter {
    TablesInteractor mInteractor;
    TablesView mView;
    Disposable mDisposable;

    public TablesPresenterImpl(TablesView view, TablesInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void getCustomers() {
        mView.showProgress();
        disposeService();
        mDisposable = mInteractor.getTablesFromApi().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Boolean>>() {
                            @Override
                            public void accept(List<Boolean> items) throws Exception {
                                handleData(items);

                            }
                        }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                errorInService();
                            }
                        });
    }

    private void errorInService() {
        mView.hideProgress();

        // try to get offline data if exists
        List<Table> items = mInteractor.getTablesFromDatabase();
        if (items.size() > 0) {
            mView.showGotOfflineData();
            mView.updateData(items);
        } else {
            mView.showError();
        }
    }

    private void handleData(List<Boolean> items) {

        List<Table> tables = mInteractor.getTablesFromApiResponse(items);
        mInteractor.insertOrUpdateTables(tables);
        mView.updateData(tables);
        mView.hideProgress();

    }

    @Override
    public void onDestroy() {
        disposeService();
    }

    @Override
    public void updateTableAvailability(int id, boolean isAvailable) {

    }

    @Override
    public void updateTableReservation(Table selectedTable) {
        if (selectedTable.isOriginallyAvailable()) {
            if (selectedTable.isAvailable()) {
                // reserve
                selectedTable.setAvailable(false);
            } else {
                //cancel reservation
                selectedTable.setAvailable(true);
            }
            mView.showTableUpdated(selectedTable.isAvailable());
            mInteractor.insertOrUpdateTableItem(selectedTable);

        }
    }

    void disposeService() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
