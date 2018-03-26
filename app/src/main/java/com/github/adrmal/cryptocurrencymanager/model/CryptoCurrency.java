package com.github.adrmal.cryptocurrencymanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrency implements Currency {
    BITCOIN("bitcoin", "BTC"),
    BITCOIN_CASH("bitcoin cash", "BCC"),
    BITCOIN_GOLD("bitcoin gold", "BTG"),
    ETHEREUM("ethereum", "ETH"),
    LISK("lisk", "LSK"),
    LITECOIN("litecoin", "LTC"),
    DASH("dash", "DASH"),
    GAME_CREDITS("game credits", "GAME"),
    RIPPLE("ripple", "XRP");

    private String name;
    private String code;

}
