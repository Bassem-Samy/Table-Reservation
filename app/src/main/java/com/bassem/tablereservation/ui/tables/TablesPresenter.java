package com.bassem.tablereservation.ui.tables;

import com.bassem.tablereservation.models.Table;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public interface TablesPresenter {
    void getTables();
    void onDestroy();
    void updateTableAvailability(int id,boolean isAvailable);

    void updateTableReservation(Table selectedTable);

    void getTablesAfterServiceUpdate();
}
