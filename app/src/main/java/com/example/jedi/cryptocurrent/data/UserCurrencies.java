package com.example.jedi.cryptocurrent.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by jedi on 12/19/2017.
 */

public class UserCurrencies {
    public  Cursor mCursor;
    private SQLiteDatabase mDb;

    public UserCurrencies(Context context) {

        CryptocurrentDbHelper cryptocurrentDbHelper = new CryptocurrentDbHelper(context);
        mDb = cryptocurrentDbHelper.getReadableDatabase();
        cryptocurrentDbHelper.onCreate(mDb);

        mCursor = getAllCards(context);

    }
    public Cursor getAllCards(Context context){
        String query = " SELECT * FROM " + CryptocurrentContract.CryptocurrentEntry.TABLE_NAME + " ORDER BY "
                + CryptocurrentContract.CryptocurrentEntry.COLUMN_CURRENCY;

        Cursor cursor = mDb.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        else{
            Toast.makeText(context, "THE CURSOR RETURNED NOTHING", Toast.LENGTH_LONG).show();
        }
        return cursor;
    }
}
