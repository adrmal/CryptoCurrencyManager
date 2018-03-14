package com.github.adrmal.cryptocurrencymanager.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextFormatter {

    public static String formatCurrency(double currencyValue) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(currencyValue);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'r'. HH:mm:ss", Locale.getDefault());
        return formatter.format(date);
    }

}
