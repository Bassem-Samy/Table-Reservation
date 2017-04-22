package com.bassem.tablereservation.ui.tables;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public interface TablesPresenter {
    void getCustomers();
    void onDestroy();
    void updateTableAvailability(int id,boolean isAvailable);
}