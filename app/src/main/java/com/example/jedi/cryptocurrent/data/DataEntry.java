package com.example.jedi.cryptocurrent.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;

import java.util.Date;
import java.util.Map;

/**
 * Created by jedi on 12/21/2017.
 */

public class DataEntry {

    private SQLiteDatabase mdb;

    public DataEntry(Context context) {
        CryptocurrentDbHelper cryptocurrentDbHelper = new CryptocurrentDbHelper(context);
        mdb = cryptocurrentDbHelper.getWritableDatabase();
        cryptocurrentDbHelper.onCreate(mdb);
    }

    public long updateDatabase(Map[] maps) {
        long returnValue = 1;
        ContentValues contentValues = new ContentValues();
        String query = " SELECT * FROM " + CryptocurrentContract.CryptocurrentEntry.TABLE_NAME + " ORDER BY "
                + CryptocurrentContract.CryptocurrentEntry.COLUMN_CURRENCY;
        Cursor cursor = mdb.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            for (Map map : maps) {
                String currency = map.get("name").toString();
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_CURRENCY, map.get("name").toString());
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_VALUE,
                        Double.parseDouble(map.get("value").toString()));
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());
                long retValue = mdb.insert(CryptocurrentContract.CryptocurrentDataEntry.TABLE_NAME, null, contentValues);
                if (retValue == -1) {
                    returnValue = -1;
                }
            }
        }
        else {
            mdb.execSQL(" delete from " + CryptocurrentContract.CryptocurrentDataEntry.TABLE_NAME);
            for (Map map : maps) {
                String currency = map.get("name").toString();
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_CURRENCY, map.get("name").toString());
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_VALUE,
                        Double.parseDouble(map.get("value").toString()));
                contentValues.put(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());
                long retValue = mdb.insert(CryptocurrentContract.CryptocurrentDataEntry.TABLE_NAME, null, contentValues);
                if (retValue == -1) {
                    returnValue = -1;
                }
            }
        }
        cursor.close();
        return returnValue;
    }

    //TODO: THE CONTEXT HERE IS JUST FOR DEBUGGING
    public Cursor fetchFromDatabase(Context context){
        String query = " SELECT * FROM " + CryptocurrentContract.CryptocurrentDataEntry.TABLE_NAME ;
        Cursor cursor = mdb.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        else{
            Toast.makeText(context, "THE CURSOR RETURNED NOTHING", Toast.LENGTH_LONG).show();
        }
        return cursor;
    }
}
