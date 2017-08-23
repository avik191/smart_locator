package com.example.hi.smartlocator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by HI on 02-Mar-17.
 */

public class TheftReceiver extends BroadcastReceiver {

    Context c;
    String id;
    TelephonyManager tm;

    @Override
    public void onReceive(Context context, Intent intent) {


        c = context;
        tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        id = app_preferences.getString("simno", tm.getSimSerialNumber());
        String phone = app_preferences.getString("loc_phone","");

        Toast.makeText(context, "SIM card ID: " + id,
                Toast.LENGTH_LONG).show();

        String simID = tm.getSimSerialNumber();
        Toast.makeText(context, "current SIM card ID: " + simID,
                Toast.LENGTH_LONG).show();

        if (simID.equals(id)) {

            Toast.makeText(c, "same sim is inserted",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(c, "diff. sim is inserted",
                    Toast.LENGTH_LONG).show();

            GPSTracker gps = new GPSTracker(context);
            if (gps.gpsoff) {
                Double latitude = gps.getlatitude();
                Double longitude = gps.getlongitude();

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone, null, "check: " + latitude + ", " + longitude,
                        null, null);
            }
        }

    }
}
