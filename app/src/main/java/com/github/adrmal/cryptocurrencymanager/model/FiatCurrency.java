package com.github.adrmal.cryptocurrencymanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FiatCurrency implements Currency {
    ZLOTY("złoty", "PLN");

    private String name;
    private String code;

}
