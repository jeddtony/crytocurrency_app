package com.example.jedi.cryptocurrent.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jedi on 12/17/2017.
 */

public class CryptocurrentDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cryptocurrent.db";

    private static final int DATABASE_VERSION = 1;

   private final String SQL_CREATE_CRYPTOCURRENT_TABLE = "CREATE TABLE IF NOT EXISTS " + CryptocurrentContract.CryptocurrentEntry.TABLE_NAME
            + " ( " + CryptocurrentContract.CryptocurrentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CryptocurrentContract.CryptocurrentEntry.COLUMN_CURRENCY + " TEXT NOT NULL, " +
            CryptocurrentContract.CryptocurrentEntry.COLUMN_USD_VALUE + " DOUBLE NOT NULL, " +
            CryptocurrentContract.CryptocurrentEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " + " ); ";

    private final String SQL_CREATE_CRYPTOCURRENT_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + CryptocurrentContract.CryptocurrentDataEntry.TABLE_NAME
            + " ( " + CryptocurrentContract.CryptocurrentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CryptocurrentContract.CryptocurrentDataEntry.COLUMN_CURRENCY + " TEXT NOT NULL, " +
            CryptocurrentContract.CryptocurrentDataEntry.COLUMN_VALUE + " DOUBLE NOT NULL, " +
            CryptocurrentContract.CryptocurrentDataEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " + " ); ";


    public CryptocurrentDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_CRYPTOCURRENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CRYPTOCURRENT_DATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
