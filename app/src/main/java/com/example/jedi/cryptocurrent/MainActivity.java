package com.example.jedi.cryptocurrent;

import android.content.AsyncTaskLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Loader;
//import android.support.v4.content.Loader;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jedi.cryptocurrent.data.CryptocurrentContract;
import com.example.jedi.cryptocurrent.data.DataEntry;
import com.example.jedi.cryptocurrent.sync.ReminderUtilities;
import com.example.jedi.cryptocurrent.utils.JsonUtils;
import com.example.jedi.cryptocurrent.utils.NetworkUtils;
import com.example.jedi.cryptocurrent.utils.NotificationUtils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        android.app.LoaderManager.LoaderCallbacks<Map[]>,
        CurrencyAdapter.CurrencyAdapterOnClickHandler
{

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    TextView mErrorMessage;
    CurrencyAdapter mCurrencyAdapter;

    private static final String LIFECYCLE_CALLBACKS_ADAPTER_KEY = "adapter";
    CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_ADAPTER_KEY)){
                Log.i("SavedInstanceState", "This is the save instance state");
                mCurrencyAdapter = (CurrencyAdapter)savedInstanceState.get(LIFECYCLE_CALLBACKS_ADAPTER_KEY);
            }
        }

        else {
            Log.i("SavedInstanceState" ,"Saved Instance state is empty");
        }

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_card_list);
        mErrorMessage = (TextView) findViewById(R.id.error_loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mCurrencyAdapter = new CurrencyAdapter(this, this);
        mRecyclerView.setAdapter(mCurrencyAdapter);

        // THIS IS TO TEST FOR PERMISSIONS FOR SOME DEVICES

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getBaseContext(), "In the context compat ", Toast.LENGTH_LONG).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.INTERNET)){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.INTERNET}, 3);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, 0);
            }
        }
        else {
            proceedAfterPermission();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ReminderUtilities.scheduleChargingReminder(this);
    }

    public void proceedAfterPermission(){
//        showLoadedData();
//        fetchFromDatabase();

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(LIFECYCLE_CALLBACKS_ADAPTER_KEY, mCurrencyAdapter);
        currencyAdapter = mCurrencyAdapter;
        Log.i("onSaveInstanceState", "The onSaveInstanceState just executed");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mCurrencyAdapter = null;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart", "This is the on Restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mCurrencyAdapter = currencyAdapter;
        Log.i("onResume", "This is the on resume");
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.i("OnRestore", "This is the onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            return true;
            Class startCreateCard = SettingsActivity.class;
            Intent startCardList = new Intent(getBaseContext(), startCreateCard);
            startActivity(startCardList);
        }

        if(id == R.id.show_notification){
//            NotificationUtils.remindUserBecauseCharging(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.create_currency) {
            Class startCreateCard = CreateCurrency.class;
            Intent startCardList = new Intent(getBaseContext(), startCreateCard);
            startActivity(startCardList);
            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Map[]> onCreateLoader(int id, Bundle args) {
//        return null;
        return new AsyncTaskLoader<Map[]>(this) {

            Map[] mCryptoData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                showLoading();

                if (mCryptoData != null) {
                    deliverResult(mCryptoData);
                } else {
                    //TODO: display the loading bar
                    showLoadedData();
                    fetchFromDatabase();
                    forceLoad();
                }

            }

            @Override
            public Map[] loadInBackground() {
//                return new Map[0];
                URL multiplePrice = NetworkUtils.getMultiplePriceUrl();
                try {
                    String results = NetworkUtils.getResponseFromApi(multiplePrice);
                    Map[] jsonData = JsonUtils.getCurrentRatesFromJson(MainActivity.this, results);
//                    Log.i("loadInBackgroud", "" + jsonData.length);
                    return jsonData;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Map[] data){
                mCryptoData = data;
                super.deliverResult(data);
            }
        };
    }
    @Override
    public void onLoadFinished(Loader<Map[]> loader, Map[] maps) {
        if(maps != null){
            showLoadedData();
            mCurrencyAdapter.swapMap(maps);
            updateDatabase(maps);
        }
        else {
            showErrorMessage();
        }

    }

    @Override
    public void onLoaderReset(Loader<Map[]> loader) {

    }

    private void showLoading (){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar .setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    private void showLoadedData(){
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void updateDatabase(Map[] maps){
        DataEntry dataEntry = new DataEntry(getBaseContext());
      long updateResult = dataEntry.updateDatabase(maps);

        if(updateResult == -1){
            Toast.makeText(getBaseContext(), "Data failed to be updated ", Toast.LENGTH_LONG).show();
        }
        else{
//            Toast.makeText(getBaseContext(), "Data updated successfully ", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchFromDatabase() {
        DataEntry dataEntry = new DataEntry(getBaseContext());
        Cursor cursor = dataEntry.fetchFromDatabase(getBaseContext());
        if (cursor != null) {
            Map[] map = new Map[cursor.getCount()];
            int count = 0;
            boolean cursorCount = cursor.moveToFirst();
            while (cursorCount) {
                String currencyName = cursor.getString(cursor.getColumnIndex(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_CURRENCY));
                double value = cursor.getDouble(cursor.getColumnIndex(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_VALUE));
                long dateTime = cursor.getLong(cursor.getColumnIndex(CryptocurrentContract.CryptocurrentDataEntry.COLUMN_TIMESTAMP));
                Map tempMap = new HashMap();
                tempMap.put("name", currencyName);
                tempMap.put("value", value);

                // WE FORMAT THE TIME
                Date date = new Date(dateTime);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(date);
                tempMap.put("time", time);

                // FORMAT FOR THE DATE
                formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateTime);
                String thisDate = formatter.format(calendar.getTime());
                tempMap.put("date", thisDate);

                map[count] = tempMap;
                cursorCount = cursor.moveToNext();
                count++;
//                Log.i("FetchData", ""+ tempMap.get("name").toString());
            }
            mCurrencyAdapter.swapMap(map);
        }
    }

    @Override
    public void onClick(Map thisCurrency) {
//        Toast.makeText(getBaseContext(), "This is the share button", Toast.LENGTH_LONG).show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Guys current " + thisCurrency.get("name").toString() + " is \n " + thisCurrency.get("value").toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Jed title"));

    }
}
