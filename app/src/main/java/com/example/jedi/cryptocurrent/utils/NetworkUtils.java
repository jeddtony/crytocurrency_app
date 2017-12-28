package com.example.jedi.cryptocurrent.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by jedi on 12/17/2017.
 */

public class NetworkUtils {
    private static final String BASE_SINGLE_PRICE_URL =
            "https://min-api.cryptocompare.com/data/price";

    private static final String BASE_MULTIPLE_PRICE_URL =
            "https://min-api.cryptocompare.com/data/pricemulti";

    private static final String COINS = "fyms";

    private static final String BOTH_COINS = "BTC%2CETH";

    private static final String SINGLE_COIN = "BTC";

    private static final String CURRENCY = "tsyms";

    private static final String currency = "USD%2CEUR";

    private static String TAG = NetworkUtils.class.getSimpleName();

    public static URL getMultiplePriceUrl(){
        Uri exchangeRateUri = Uri.parse(BASE_MULTIPLE_PRICE_URL).
                buildUpon()
                .appendPath(BOTH_COINS)
                .appendPath(currency)
                .build();
//        Log.i("Jedi ", " " + exchangeRateUri);

        try{
//            URL crytocompare = new URL(exchangeRateUri.toString());
            URL crytocompare = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR");
            Log.i(TAG, "THE URL IS: " + crytocompare);
            return crytocompare;
        }
        catch (MalformedURLException exception){
            exception.printStackTrace();
            return null;
        }

    }

    public static URL getSinglePriceUrl(Context context){
        Uri exchangeRateUri = Uri.parse(BASE_SINGLE_PRICE_URL).
                buildUpon()
                .appendQueryParameter(COINS, SINGLE_COIN)
                .appendQueryParameter(CURRENCY, currency)
                .build();

        try{
            URL crytocompare = new URL(exchangeRateUri.toString());
            Log.i(TAG, "THE URL IS: " + crytocompare);
            return crytocompare;
        }
        catch (MalformedURLException exception){
            exception.printStackTrace();
            return null;
        }

    }


    public static String getResponseFromApi(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if(hasInput){
                response = scanner.next();
            }
            scanner.close();
            return  response;
        }
        finally {
            httpURLConnection.disconnect();
        }
    }
}
