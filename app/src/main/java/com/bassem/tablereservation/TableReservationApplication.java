package com.bassem.tablereservation;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public class TableReservationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
