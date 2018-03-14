package com.github.adrmal.cryptocurrencymanager.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.github.adrmal.cryptocurrencymanager.R;
import com.github.adrmal.cryptocurrencymanager.model.Currency;

public class ActivityUtils {

    private Activity activity;
    private Context context;

    public ActivityUtils(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public ActivityUtils(Context context) {
        this.context = context;
    }

    public void showToast(final int textResourceId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(), textResourceId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveFloatToSharedPreferences(Currency currency, float value) {
        SharedPreferences preferences = context.getSharedPreferences(getAppName(), Context.MODE_PRIVATE);
        preferences.edit().putFloat(currency.getCode(), value).apply();
    }

    public float getFloatFromSharedPreferences(Currency currency) {
        SharedPreferences preferences = context.getSharedPreferences(getAppName(), Context.MODE_PRIVATE);
        return preferences.getFloat(currency.getCode(), -1);
    }

    private String getAppName() {
        return context.getString(R.string.app_name);
    }

}
