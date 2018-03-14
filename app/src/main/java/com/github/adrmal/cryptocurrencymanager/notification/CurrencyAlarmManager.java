package com.github.adrmal.cryptocurrencymanager.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class CurrencyAlarmManager {

    private Context context;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public CurrencyAlarmManager(Context context) {
        this.context = context;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CurrencyBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void setAlarm() {
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5*60*1000, alarmIntent);

        ComponentName bootReceiver = new ComponentName(context, CurrencyBootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    public void cancelAlarm() {
        if(alarmManager != null) {
            alarmManager.cancel(alarmIntent);
        }

        ComponentName bootReceiver = new ComponentName(context, CurrencyBootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
