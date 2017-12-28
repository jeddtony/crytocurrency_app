package com.example.jedi.cryptocurrent.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jedi on 12/17/2017.
 */

public class CryptocurrentContract {
    public static final String AUTHORITY = "com.example.jedi.cryptocurrent";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_USER_CURRENCY = "Cryptocurrency";
    public static final String PATH_SAVE_CURRENCY = "CryptocurrencyData";

    private CryptocurrentContract(){}

    public static class CryptocurrentEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_CURRENCY).build();
        public static final String TABLE_NAME = "Cryptocurrencies";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_USD_VALUE = "usd_value";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    public static class CryptocurrentDataEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SAVE_CURRENCY).build();
        public static final String TABLE_NAME = "CurrencyData";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_VALUE = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
