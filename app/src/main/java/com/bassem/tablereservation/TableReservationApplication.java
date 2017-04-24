package com.bassem.tablereservation;

import android.app.Application;

import com.bassem.tablereservation.background.UpdateTablesInBackgroundHelper;
import com.bassem.tablereservation.background.UpdateTablesIntentService;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class TableReservationApplication extends Application {
    private static final int UPDATE_TABLES_SERVICE_ID = 1;
    private static final int UPDATE_TABLES_SERVICE_MINUTES_INTERVAL = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        registerUpdateTablesService();
    }

    void registerUpdateTablesService() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MINUTE, UPDATE_TABLES_SERVICE_MINUTES_INTERVAL);
        UpdateTablesInBackgroundHelper updateTablesInBackgroundHelper = new UpdateTablesInBackgroundHelper();
        updateTablesInBackgroundHelper.createUpdateTablesAlarm(this,
                new UpdateTablesIntentService(UpdateTablesIntentService.NAME),
                UPDATE_TABLES_SERVICE_ID,
                System.currentTimeMillis(),
                UPDATE_TABLES_SERVICE_MINUTES_INTERVAL);
    }

}
