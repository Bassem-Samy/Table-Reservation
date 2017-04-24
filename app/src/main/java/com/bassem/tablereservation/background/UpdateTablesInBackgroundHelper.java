package com.bassem.tablereservation.background;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

/**
 * Created by Bassem Samy on 4/24/2017.
 * a class that uses alarm manager to schedule a service to run on a given interval
 */

public class UpdateTablesInBackgroundHelper {
    /**
     * @param context                         the context to start the alarm with
     * @param service                         the service to start
     * @param serviceId                       the service id
     * @param whenToStartInSystemMilliseconds time in system millis to start
     * @param repeatIntervalInMinutes         repeat interval
     */
    public void createUpdateTablesAlarm(Context context, IntentService service, int serviceId, long whenToStartInSystemMilliseconds, int repeatIntervalInMinutes) {
        long intervalInMillis = 1000 * 60 * repeatIntervalInMinutes;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(context, UpdateTablesIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                serviceId,
                serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC,
                whenToStartInSystemMilliseconds,
                intervalInMillis,
                pendingIntent);
    }
}
