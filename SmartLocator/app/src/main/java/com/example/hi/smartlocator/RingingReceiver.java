package com.example.hi.smartlocator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import static java.lang.Thread.sleep;

/**
 * Created by HI on 27-Feb-17.
 */

public class RingingReceiver extends BroadcastReceiver {

    DBAdapter db;
    SharedPreferences app_preferences;
    static MediaPlayer mp;


    @Override
    public void onReceive(Context context, Intent intent) {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        String bdy="";
        mp=MediaPlayer.create(context,R.raw.spd);

        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if (i==0) {
                    //---get the sender address/phone number---
                    str += msgs[i].getOriginatingAddress();
                    Toast.makeText(context, str, Toast.LENGTH_LONG).show();

//                    if(str.equals("TM-WAYSMS"))
//                        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                }
            //---get the message body---
                bdy += msgs[i].getMessageBody().toString();
            }

            db=new DBAdapter(context);
            app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String uname=app_preferences.getString("uname","");
            String phone = app_preferences.getString("loc_phone","");

            String ring_name= uname+"_ring";
            String locate_name=uname+"_locate";
            String start_ring=uname+"_startring";

            db.open();
            Cursor c = db.getAllFriends();
            if(c!=null) {
                if (c.moveToFirst()) {
                    do {
                        String ph = "+91"+c.getString(2);
                        if (str.equals(ph)){
                           // Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                            if(ring_name.equals(bdy))
                            {
                                AudioManager myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                Toast.makeText(context, "Now in Ringing Mode",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                            if(locate_name.equals(bdy)) {
                                GPSTracker gps = new GPSTracker(context);
                                if (gps.gpsoff) {
                                    Double latitude = gps.getlatitude();
                                    Double longitude = gps.getlongitude();
                                    Toast.makeText(context, "lat: " + latitude + " long: " + longitude, Toast.LENGTH_SHORT).show();

                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage(phone, null, "check: " + latitude + ", " + longitude,
                                            null, null);
                                }
                                break;
                            }
                            if(start_ring.equals(bdy))
                            {
                                AudioManager myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                Toast.makeText(context, "Now in Ringing Mode",
                                        Toast.LENGTH_LONG).show();
                                Thread th=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mp.start();
                                        try {
                                            sleep(10000);
                                            mp.stop();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                th.start();
                                break;
                            }

                        }
                    } while (c.moveToNext());
                }
            }
                db.close();


        }
    }
}
