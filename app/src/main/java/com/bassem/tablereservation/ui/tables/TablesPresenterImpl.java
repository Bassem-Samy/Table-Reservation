package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.background.UpdateTablesInBackgroundHelper;
import com.bassem.tablereservation.models.Table;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem Samy on 4/22/2017.
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
    public void getTables() {
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
        UpdateTablesInBackgroundHelper helper = new UpdateTablesInBackgroundHelper();

    }

    @Override
    public void onDestroy() {
        disposeService();
    }

    /**
     * Update a table reservation if it's available set to reserved and vice versa
     * @param selectedTable
     */
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

    /**
     * called after the background service notifies the view that it has updated the tables from db
     */
    @Override
    public void getTablesAfterServiceUpdate() {
        List<Table> items = mInteractor.getTablesFromDatabase();
        if (items != null && items.size() > 0) {
            mView.showUpdatedDataFromService();
            mView.updateData(items);
        }
    }

    @Override
    public int getTablesFromDatabase() {
        List<Table> items = mInteractor.getTablesFromDatabase();
        if (items != null && items.size() > 0) {
            mView.updateData(items);
            return items.size();
        }
        return 0;
    }

    void disposeService() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
