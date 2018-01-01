package com.example.jedi.cryptocurrent.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jedi.cryptocurrent.data.CryptocurrentContract;
import com.example.jedi.cryptocurrent.data.UserCurrencies;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jedi on 12/17/2017.
 */

public class CurrencyUtils {
    public static String USD_BTC = null ;
    public static String  USD_ETH = null;
    public static String EUR_ETH = null;
    public static String EUR_BTC = null;

    public static Map getUsdBtc(){
        Map<String, String> localRate = new HashMap<>();
        localRate.put("name", "USD/BTC");
        localRate.put("value", USD_BTC);
        localRate.put("time", DateFormat.getTimeInstance().format(new Date()));
        localRate.put("date", DateFormat.getDateInstance().format(new Date()));
        return localRate;
    }
    public static Map getUsdEth(){
        Map<String, String> localRate = new HashMap<>();
        localRate.put("name", "USD/ETH");
        localRate.put("value", USD_ETH);
        localRate.put("time", DateFormat.getTimeInstance().format(new Date()));
        localRate.put("date", DateFormat.getDateInstance().format(new Date()));
        return localRate;
    }

    public static Map getEurBtc(){
        Map<String, String> localRate = new HashMap<>();
        localRate.put("name", "EUR/BTC");
        localRate.put("value", EUR_BTC);
        localRate.put("time", DateFormat.getTimeInstance().format(new Date()));
        localRate.put("date", DateFormat.getDateInstance().format(new Date()));
        return localRate;
    }


    public static Map getEurEth(){
        Map<String, String> localRate = new HashMap<>();
        localRate.put("name", "EUR/ETH");
        localRate.put("value", EUR_ETH);
        localRate.put("time", DateFormat.getTimeInstance().format(new Date()));
        localRate.put("date", DateFormat.getDateInstance().format(new Date()));
        return localRate;
    }
    public static Map[] getAllCurrencies(){
        Map[] map = {getUsdBtc(), getUsdEth(), getEurBtc(), getEurEth()};
        return map;
    }

    public static Map[] getUserCurrencies(Context context){
        UserCurrencies dc = new UserCurrencies(context);
        Cursor cursor = dc.mCursor;
        Map[] map;

        if (cursor.getCount() != 0){
            //TODO: Work on the next line. Try to use generics
            map = new Map[cursor.getCount() * 2];
            int count = 0;
            Log.i("getUserCurrencies", "Now in the getUserCurrencies if statement");
            Log.i("getUserCurrencies", "the cursor length is "+cursor.getCount());
            boolean cursorCount = cursor.moveToFirst() ;
        while (cursorCount) {
            String currencyName = cursor.getString(cursor.getColumnIndex(CryptocurrentContract.CryptocurrentEntry.COLUMN_CURRENCY));
            Log.i("CurrencyName" , currencyName);
            Double usdEquivalence = cursor.getDouble(cursor.getColumnIndex(CryptocurrentContract.CryptocurrentEntry.COLUMN_USD_VALUE));
            Map tempBtc = currencyToMapBtc(currencyName, usdEquivalence);
            Map tempEth = currencyToMapEth(currencyName, usdEquivalence);
            map[count] = tempBtc;
            count++;
            map[count] = tempEth;
            count++;

            cursorCount = cursor.moveToNext();
        }
            return map;
        }
        else {
            return null;
        }

    }

    public static Map currencyToMapBtc(String currencyName, Double usdEquivalence){
        double currencyValue = Double.parseDouble(USD_BTC) * usdEquivalence;
        Log.i("USD_BTC" , USD_BTC);
        Log.i("usdEquivalence", "" + usdEquivalence);
        Log.i("currencyValue", "" + currencyValue);
        Map<String, String> map = new HashMap<>();
        map.put("name", currencyName + "/Btc");
        map.put("value", "" + currencyValue);
        map.put("time", DateFormat.getTimeInstance().format(new Date()));
        map.put("date", DateFormat.getDateInstance().format(new Date()));
        return map;
    }

    public static Map currencyToMapEth(String currencyName, Double usdEquivalence){
        double currencyValue = Double.parseDouble(USD_ETH) * usdEquivalence;
        Log.i("USD_ETH" , USD_ETH);
        Log.i("usdEquivalence", "" + usdEquivalence);
        Log.i("currencyValue", "" + currencyValue);

        Map<String, String> map = new HashMap<>();
        map.put("name", currencyName + "/Eth");
        map.put("value", "" + currencyValue);
        map.put("time", DateFormat.getTimeInstance().format(new Date()));
        map.put("date", DateFormat.getDateInstance().format(new Date()));
        return map;
    }
}

