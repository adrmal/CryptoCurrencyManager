package com.github.adrmal.cryptocurrencymanager.view.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.adrmal.cryptocurrencymanager.R;
import com.github.adrmal.cryptocurrencymanager.model.CryptoCurrency;
import com.github.adrmal.cryptocurrencymanager.model.Currency;
import com.github.adrmal.cryptocurrencymanager.util.JsonUtils;
import com.github.adrmal.cryptocurrencymanager.model.FiatCurrency;
import com.github.adrmal.cryptocurrencymanager.util.TextFormatter;
import com.github.adrmal.cryptocurrencymanager.notification.CurrencyAlarmManager;
import com.github.adrmal.cryptocurrencymanager.notification.CurrencyBroadcastReceiver;
import com.github.adrmal.cryptocurrencymanager.rest.RESTClient;
import com.github.adrmal.cryptocurrencymanager.util.ActivityUtils;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends Activity {

    private RESTClient restClient;
    private ActivityUtils utils;
    private TextView currentValueTextView;
    private ImageView refreshButton;
    private TextView dateTextView;
    private Spinner currencySpinner;
    private EditText notificationValueEditText;
    private ImageView setNotificationButton;
    private ImageView deleteNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarmManagerIfNotRunning();

        initializeUi();
        initializeUiListeners();
        restClient = new RESTClient();
        utils = new ActivityUtils(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendRequestToWebservice();
        setNotificationValueEditTextFromSharedPref();
    }

    private void initializeUi() {
        currentValueTextView = findViewById(R.id.currentValue);
        refreshButton = findViewById(R.id.refresh);
        dateTextView = findViewById(R.id.date);
        currencySpinner = findViewById(R.id.currency);
        CurrencyAdapter adapter = new CurrencyAdapter(getApplicationContext(), R.layout.currency_spinner_element, CryptoCurrency.values());
        currencySpinner.setAdapter(adapter);
        notificationValueEditText = findViewById(R.id.notificationValue);
        setNotificationButton = findViewById(R.id.setNotification);
        deleteNotificationButton = findViewById(R.id.deleteNotification);
    }

    private void initializeUiListeners() {
        initializeRefreshButtonListener();
        initializeSetNotificationButtonListener();
        initializeDeleteNotificationButtonListener();
        initializeSpinnerListener();
    }

    private void initializeRefreshButtonListener() {
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestToWebservice();
            }
        });
    }

    private void initializeSetNotificationButtonListener() {
        setNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float notificationValue = Float.parseFloat(notificationValueEditText.getText().toString());
                    utils.saveFloatToSharedPreferences(getChosenCurrency(), notificationValue);
                    utils.showToast(R.string.notificationSetToast);
                }
                catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initializeDeleteNotificationButtonListener() {
        deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationValueEditText.setText("");
                utils.saveFloatToSharedPreferences(getChosenCurrency(), -1);
                utils.showToast(R.string.notificationDeleteToast);
            }
        });
    }

    private void initializeSpinnerListener() {
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendRequestToWebservice();
                setNotificationValueEditTextFromSharedPref();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sendRequestToWebservice() {
        restClient.getExchangeRate(getChosenCurrency(), FiatCurrency.ZLOTY, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                utils.showToast(R.string.webServiceError);
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            double exchangeRate = JsonUtils.parseExchangeRate(response.body().string());
                            String formattedExchangeRate = TextFormatter.formatCurrency(exchangeRate);
                            currentValueTextView.setText(formattedExchangeRate);
                            String formattedDate = TextFormatter.formatDate(new Date());
                            dateTextView.setText(formattedDate);
                        }
                        catch(IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    private void setNotificationValueEditTextFromSharedPref() {
        double notificationValue = utils.getFloatFromSharedPreferences(getChosenCurrency());
        if(notificationValue >= 0) {
            notificationValueEditText.setText(String.valueOf(notificationValue));
        }
        else {
            notificationValueEditText.setText("");
        }
    }

    private void setAlarmManagerIfNotRunning() {
        Intent intent = new Intent(getApplicationContext(), CurrencyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE);
        if(pendingIntent == null) {
            CurrencyAlarmManager alarmManager = new CurrencyAlarmManager(getApplicationContext());
            alarmManager.setAlarm();
        }
    }

    private Currency getChosenCurrency() {
        int chosenCurrencyIndex = currencySpinner.getSelectedItemPosition();
        return CryptoCurrency.values()[chosenCurrencyIndex];
    }

}
