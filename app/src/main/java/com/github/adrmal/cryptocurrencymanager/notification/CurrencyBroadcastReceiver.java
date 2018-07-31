package com.github.adrmal.cryptocurrencymanager.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.github.adrmal.cryptocurrencymanager.R;
import com.github.adrmal.cryptocurrencymanager.model.CryptoCurrency;
import com.github.adrmal.cryptocurrencymanager.model.Currency;
import com.github.adrmal.cryptocurrencymanager.util.JsonUtils;
import com.github.adrmal.cryptocurrencymanager.model.FiatCurrency;
import com.github.adrmal.cryptocurrencymanager.util.TextFormatter;
import com.github.adrmal.cryptocurrencymanager.rest.RESTClient;
import com.github.adrmal.cryptocurrencymanager.util.ActivityUtils;
import com.github.adrmal.cryptocurrencymanager.view.activity.MainActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CurrencyBroadcastReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {
        checkCurrencyAndShowNotificationIfNeeded(context, CryptoCurrency.BITCOIN);
        checkCurrencyAndShowNotificationIfNeeded(context, CryptoCurrency.ETHEREUM);
    }

    private void checkCurrencyAndShowNotificationIfNeeded(final Context context, final Currency currency) {
        final ActivityUtils utils = new ActivityUtils(context);
        RESTClient restClient = new RESTClient();
        restClient.getExchangeRate(currency, FiatCurrency.ZLOTY, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                double exchangeRate = JsonUtils.parseExchangeRate(response.body().string());
                double minimumValue = utils.getFloatFromSharedPreferences(currency);
                if(minimumValue >= 0 && exchangeRate > minimumValue) {
                    showNotification(context, currency, exchangeRate, minimumValue);
                }
            }
        });
    }

    private void showNotification(Context context, Currency currency, double exchangeRate, double minimumValue) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String notificationTitle =
                context.getString(R.string.notificationTitle1)
                + " " + TextFormatter.formatCurrency(minimumValue)
                + " " + context.getString(R.string.notificationTitle2)
                + " " + currency.getCode();
        String notificationText = TextFormatter.formatCurrency(exchangeRate);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.icon_24dp)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

}
