package com.github.adrmal.cryptocurrencymanager.view.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.adrmal.cryptocurrencymanager.R;
import com.github.adrmal.cryptocurrencymanager.model.CryptoCurrency;
import com.github.adrmal.cryptocurrencymanager.model.Currency;

public class CurrencyAdapter extends ArrayAdapter<CryptoCurrency> {

    public CurrencyAdapter(Context context, int resource, CryptoCurrency[] objects) {
        super(context, resource, objects);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Currency currency = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.currency_spinner_element, parent, false);
        }
        TextView currencyName = convertView.findViewById(R.id.currencyName);
        currencyName.setText(currency.getName());

        return convertView;
    }

}
