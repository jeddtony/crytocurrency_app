package com.example.jedi.cryptocurrent.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jedi.cryptocurrent.MainActivity;
import com.example.jedi.cryptocurrent.utils.CurrencyUtils;
import com.example.jedi.cryptocurrent.utils.JsonUtils;
import com.example.jedi.cryptocurrent.utils.NetworkUtils;
import com.example.jedi.cryptocurrent.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Map;

/**
 * Created by jedi on 12/24/2017.
 */

public class UpdateTasks{

//    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
//    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
//    static final String ACTION_CHARGING_REMINDER = "charging-reminder";

    public static final String CHECK_BTC_DOLLAR_UPDATE = "bitcoin_dollar_update";
    public static final String CHECK_BTC_EURO_UPDATE = "bitcoin_euro_update";
    public static final String CHECK_ETH_DOLLAR_UPDATE = "ethereum_dollar_update";
    public static final String CHECK_ETH_EURO_UPDATE = "ethereum_euro_update";

    public static void executeTask(Context context, String action, Double targetValue) {
//        if (ACTION_INCREMENT_WATER_COUNT.equals(action)) {
//            incrementWaterCount(context);
//        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
//            NotificationUtils.clearAllNotifications(context);
//        } else if (ACTION_CHARGING_REMINDER.equals(action)) {
//            issueChargingReminder(context);
//        }

//        NotificationUtils.remindUserBecauseCharging(context, "Bitcoin Dollar Rate Achieved",
//                "Goodnews the market just crossed your target. \n Trade Now!!");

        URL multiplePrice = NetworkUtils.getMultiplePriceUrl();
        try {
            String results = NetworkUtils.getResponseFromApi(multiplePrice);

            if(CHECK_BTC_DOLLAR_UPDATE.equals(action)){
               String onlineRate = JsonUtils.getBtcDollar(results);
                Double currentValue = Double.parseDouble(onlineRate);
                if (currentValue >= targetValue ){
                    NotificationUtils.remindUserBecauseCharging(context, "Bitcoin Dollar Rate Achieved",
                            "Goodnews the market just crossed your target. \n Trade Now!!");
                }
            }
            if(CHECK_BTC_EURO_UPDATE.equals(action)){
                String onlineRate = JsonUtils.getBtcDollar(results);
                Double currentValue = Double.parseDouble(onlineRate);
                if (currentValue >= targetValue ){
                    NotificationUtils.remindUserBecauseCharging(context, "Bitcoin Euro Rate Achieved",
                            "Goodnews the market just crossed your target. \n Trade Now!!");
                }
            }

            if(CHECK_ETH_DOLLAR_UPDATE.equals(action)){
                String onlineRate = JsonUtils.getBtcDollar(results);
                Double currentValue = Double.parseDouble(onlineRate);
                if (currentValue >= targetValue ){
                    NotificationUtils.remindUserBecauseCharging(context, "Ethereum Dollar Rate Achieved",
                            "Goodnews the market just crossed your target. \n Trade Now!!");
                }
            }
            if(CHECK_ETH_EURO_UPDATE.equals(action)){
                String onlineRate = JsonUtils.getBtcDollar(results);
                Double currentValue = Double.parseDouble(onlineRate);
                if (currentValue >= targetValue ){
                    NotificationUtils.remindUserBecauseCharging(context, "Ethereum Euro Rate Achieved",
                            "Goodnews the market just crossed your target. \n Trade Now!!");
                }
            }

        }
        catch (Exception exception){
            exception.printStackTrace();
            return ;
        }

    }

//    private static void incrementWaterCount(Context context) {
//        PreferenceUtilities.incrementWaterCount(context);
//        NotificationUtils.clearAllNotifications(context);
//    }

    // COMPLETED (2) Create an additional task for issuing a charging reminder notification.
    // This should be done in a similar way to how you have an action for incrementingWaterCount
    // and dismissing notifications. This task should both create a notification AND
    // increment the charging reminder count (hint: there is a method for this in PreferenceUtilities)
    // When finished, you should be able to call executeTask with the correct parameters to execute
    // this task. Don't forget to add the code to executeTask which actually calls your new task!

//    private static void issueChargingReminder(Context context) {
//        PreferenceUtilities.incrementChargingReminderCount(context);
//        NotificationUtils.remindUserBecauseCharging(context);
//    }


}
