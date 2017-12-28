package com.example.jedi.cryptocurrent.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by jedi on 12/17/2017.
 */

public class JsonUtils {
    private static String BTC = "BTC";
    private static String USD = "USD";
    private static String EUR = "EUR";

    private static String ETH = "ETH";

    public static Map[] getCurrentRatesFromJson(Context context, String currencyJsonStr)
            throws JSONException {

        String[] parsedCurrentRate = new String[2];

        JSONObject cryptoResults = new JSONObject(currencyJsonStr);

        JSONObject btcRates = cryptoResults.getJSONObject(BTC);
        String usdBtcRate = btcRates.getString(USD);
            String eurBtcRate = btcRates.getString(EUR);

        JSONObject ethRates = cryptoResults.getJSONObject(ETH);
        String usdEthRate = ethRates.getString(USD);
            String eurEthRate = ethRates.getString(EUR);

//            parsedCurrentRate[0] = usdBtcRate + " - " + eurBtcRate;
//            parsedCurrentRate[1] = usdEthRate + " - " + eurEthRate;
//            return parsedCurrentRate;
        return getAllCurrencies(context, usdBtcRate, usdEthRate, eurBtcRate, eurEthRate);

    }

    public static Map[] getAllCurrencies(Context context, String usdBtcRate, String usdEthRate, String eurBtcRate, String eurEthRate) {
        CurrencyUtils.USD_BTC = usdBtcRate;
        CurrencyUtils.USD_ETH = usdEthRate;
        CurrencyUtils.EUR_BTC = eurBtcRate;
        CurrencyUtils.EUR_ETH = eurEthRate;

        Map[] defaultCurrency = CurrencyUtils.getAllCurrencies();
        Map[] userCurrency = CurrencyUtils.getUserCurrencies(context);
        if (userCurrency != null) {
            return concat(defaultCurrency, userCurrency);
        }
        else {
            return defaultCurrency;
        }
    }

    public static Map[] concat(Map[] a, Map[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Map[] c= new Map[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }


    public static String getBtcDollar(String currencyJsonStr) {
        try {
            JSONObject cryptoResults = new JSONObject(currencyJsonStr);

            JSONObject btcRates = cryptoResults.getJSONObject(BTC);
            String usdBtcRate = btcRates.getString(USD);

            return usdBtcRate;
        }
        catch (JSONException jsonException){
            jsonException.printStackTrace();
            return null;
        }

    }

    public static String getBtcEuro(String currencyJsonStr){
         try {
            JSONObject cryptoResults = new JSONObject(currencyJsonStr);

            JSONObject btcRates = cryptoResults.getJSONObject(BTC);
            String eurBtcRate = btcRates.getString(EUR);
            return eurBtcRate;
        }
        catch (JSONException jsonException){
            jsonException.printStackTrace();
            return null;
        }
    }

    public static String getEthDollar(String currencyJsonStr){
        try {
            JSONObject cryptoResults = new JSONObject(currencyJsonStr);

            JSONObject ethRates = cryptoResults.getJSONObject(ETH);
            String usdEthRate = ethRates.getString(USD);
            return usdEthRate;
        }
        catch (JSONException jsonException){
            jsonException.printStackTrace();
            return null;
        }
    }

    public static String  getEthEuro(String currencyJsonStr){
        try {
            JSONObject cryptoResults = new JSONObject(currencyJsonStr);

            JSONObject ethRates = cryptoResults.getJSONObject(ETH);
            String eurEthRate = ethRates.getString(EUR);
            return eurEthRate;
        }
        catch (JSONException jsonException){
            jsonException.printStackTrace();
            return null;
        }
    }
}
