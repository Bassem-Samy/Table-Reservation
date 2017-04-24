package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.models.Table;

import java.util.List;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface TablesView {
    void updateData(List<Table> items);
    void showProgress();
    void hideProgress();
    void showError();
    void showGotOfflineData();

    void showTableUpdated(boolean available);

    void showUpdatedDataFromService();
}
