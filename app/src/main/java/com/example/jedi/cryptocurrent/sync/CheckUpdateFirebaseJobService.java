package com.example.jedi.cryptocurrent.sync;

//import android.app.job.JobParameters;
//import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.example.jedi.cryptocurrent.R;
import com.example.jedi.cryptocurrent.utils.NotificationUtils;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by jedi on 12/24/2017.
 */

public class CheckUpdateFirebaseJobService extends JobService{

    private AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

//        Toast.makeText(this, "This is the CheckUpdateFirebaseJobService",Toast.LENGTH_LONG).show();

//        NotificationUtils.remindUserBecauseCharging(this, "Bitcoin Dollar Rate Achieved",
//                "Goodnews the market just crossed your target. \n Trade Now!!");


        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                setupSharedPreference();
                return null;
            }

            @Override
            protected void onPostExecute(Object o){
                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i("CheckUpdateFJS", "this is the firebase job service");
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }

    public void setupSharedPreference(){
        Log.i("CheckUpdateFJS", "this is the firebase job service");
//        Toast.makeText(this, "This is the setupSharedPreference", Toast.LENGTH_LONG).show();
//        NotificationUtils.remindUserBecauseCharging(this, "Bitcoin Dollar Rate Achieved",
//                "Goodnews the market just crossed your target. \n Trade Now!!");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getString(R.string.switch_btc), true)){
            Double targetValue = Double.parseDouble(sharedPreferences.getString(getString(R.string.pref_btc_edit_text_key),
                    getString(R.string.pref_default_display_name)));
//            Toast.makeText(getBaseContext(), "It can connect to internet", Toast.LENGTH_LONG ).show();
            String action = sharedPreferences.getString(getString(R.string.pref_btc_list_key), "1"  );
            if(action.equals("1")){
                UpdateTasks.executeTask(this, UpdateTasks.CHECK_BTC_DOLLAR_UPDATE, targetValue);
            }
            else if(action.equals("2")){
                UpdateTasks.executeTask(this, UpdateTasks.CHECK_BTC_EURO_UPDATE, targetValue);
            }
        }

        if(sharedPreferences.getBoolean(getString(R.string.switch_eth), true)){
            Double targetValue = Double.parseDouble(sharedPreferences.getString(getString(R.string.pref_eth_edit_text_key),
                    getString(R.string.pref_default_eth_name)));
//            Toast.makeText(getBaseContext(), "It can connect to internet", Toast.LENGTH_LONG ).show();
            String action = sharedPreferences.getString(getString(R.string.pref_eth_list_key), "1"  );
            if(action.equals("1")){
                UpdateTasks.executeTask(this, UpdateTasks.CHECK_ETH_DOLLAR_UPDATE, targetValue);
            }
            else if(action.equals("2")){
                UpdateTasks.executeTask(this, UpdateTasks.CHECK_ETH_EURO_UPDATE, targetValue);
            }
        }
    }
}
