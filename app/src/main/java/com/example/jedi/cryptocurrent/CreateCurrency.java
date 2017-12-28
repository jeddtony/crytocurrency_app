package com.example.jedi.cryptocurrent;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jedi.cryptocurrent.data.CryptocurrentContract;
import com.example.jedi.cryptocurrent.data.CryptocurrentDbHelper;

public class CreateCurrency extends AppCompatActivity {

    EditText mInputCurrency;
    EditText mDollarEquivalence;
    String mInputCurrencyValue;
    Double mDollarEquivalenceValue;
    private SQLiteDatabase mdb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_currency);
        CryptocurrentDbHelper cryptocurrentDbHelper = new CryptocurrentDbHelper(this);
        mdb = cryptocurrentDbHelper.getWritableDatabase();
        cryptocurrentDbHelper.onCreate(mdb);

        mInputCurrency = (EditText) findViewById(R.id.input_currency);
        mDollarEquivalence = (EditText) findViewById(R.id.dollar_equivalence);
    }

    public void saveCurrency(View view){
        if(mInputCurrency.getText() == null){
            Toast.makeText(this, "Please enter currency", Toast.LENGTH_LONG).show();
        }
        else if(mDollarEquivalence.getText() == null){
            Toast.makeText(this, "Please enter dollar equivalence", Toast.LENGTH_LONG).show();
        }

        else {
            mInputCurrencyValue = mInputCurrency.getText().toString();
            mDollarEquivalenceValue = Double.parseDouble(mDollarEquivalence.getText().toString());
           long insertSuccess =  insertCard();
            if(insertSuccess != -1){
                Toast.makeText(this, "Currency added successfully", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Sorry unable  to save now. Please try again later", Toast.LENGTH_LONG).show();
            }
        }

    }

    public long insertCard(){
        ContentValues cv = new ContentValues();
        cv.put(CryptocurrentContract.CryptocurrentEntry.COLUMN_CURRENCY, mInputCurrencyValue);
        cv.put(CryptocurrentContract.CryptocurrentEntry.COLUMN_USD_VALUE, mDollarEquivalenceValue);

        return mdb.insert(CryptocurrentContract.CryptocurrentEntry.TABLE_NAME, null, cv);

    }
}
