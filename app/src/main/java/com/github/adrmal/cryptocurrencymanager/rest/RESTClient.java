package com.github.adrmal.cryptocurrencymanager.rest;

import com.github.adrmal.cryptocurrencymanager.model.Currency;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RESTClient {

    private OkHttpClient okHttpClient;
    private static final String BASE_URL = "https://www.bitbay.net/API/Public/";

    public RESTClient() {
        okHttpClient = new OkHttpClient();
    }

    public void getExchangeRate(Currency currency1, Currency currency2, Callback callback) {
        String requestUrl = currency1.getCode() + currency2.getCode() + "/ticker.json";
        webServiceGETMethod(callback, requestUrl);
    }

    private void webServiceGETMethod(Callback callback, String requestUrl) {
        Request request = new Request.Builder()
                .url(BASE_URL + requestUrl)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
