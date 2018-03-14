package com.github.adrmal.cryptocurrencymanager.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

    public static double parseExchangeRate(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        String exchangeRateAsString = jsonObject.get("last").getAsString();
        return Double.parseDouble(exchangeRateAsString);
    }

}
