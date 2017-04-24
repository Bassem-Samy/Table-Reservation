package com.bassem.tablereservation.background;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bassem.tablereservation.database.DatabaseHelper;
import com.bassem.tablereservation.database.DatabaseOperations;

import io.realm.Realm;

/**
 * Created by Bassem Samy on 4/24/2017.
 */

public class UpdateTablesIntentService extends IntentService {
    public static final String NAME = "update_tables_intent_service";
    public static final String ACTION = "com.bassem.tablereservation.backgroun.UpdateTablesIntentService";
    public static final String RESULT_CODE = "result_code";
    public static final String RESULT_VALUE = "result_value";

    public UpdateTablesIntentService() {
        super(NAME);
    }

    public UpdateTablesIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm = Realm.getDefaultInstance();
        DatabaseOperations databaseHelper = new DatabaseHelper(realm);
        boolean successful = databaseHelper.setAllTablesReserved();
        Log.e("service", "successfull: " + Boolean.toString(successful));
        if (successful) {
            Intent notifyUIIntent = new Intent(ACTION);
            notifyUIIntent.putExtra(RESULT_CODE, Activity.RESULT_OK);
            notifyUIIntent.putExtra(RESULT_VALUE, successful);
            LocalBroadcastManager.getInstance(this).sendBroadcast(notifyUIIntent);
        }
    }
}
