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
    RIPPLE("ripple", "XRP"),
    KZCASH("kzcash", "KZC"),
    INFINITY_ECONOMICS("infinity economics", "XIN"),
    MONERO("monero", "XMR"),
    ZCASH("zcash", "ZEC"),
    GOLEM("golem", "GNT"),
    OMISEGO("omisego", "OMG"),
    FUTUROCOIN("futurocoin", "FTO"),
    ZERO_X("0x", "ZRX"),
    TENX("tenx", "PAY"),
    BASIC_ATTENTION_TOKEN("basic attention token", "BAT"),
    AUGUR("augur", "REP"),
    NEUMARK("neumark", "NEU"),
    TRON("tron", "TRX"),
    AMLT("amlt", "AMLT"),
    EXPERTY("experty", "EXY"),
    BOBS_REPAIR("bobs repair", "BOB"),
    LISK_MACHINE_LEARNING("lisk machine learning", "LML");

    private String name;
    private String code;

}
