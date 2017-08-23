package com.example.hi.smartlocator;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

public class simcard extends AppCompatActivity {

    EditText simno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simcard);

        simno= (EditText) findViewById(R.id.simno);

    }

    public void generate(View v)
    {
        if ((ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(simcard.this,
                Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(simcard.this,
                    new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS
                            ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WAKE_LOCK},10);

        }
        else
        {
	        TelephonyManager tm = (TelephonyManager)
			        getSystemService(Context.TELEPHONY_SERVICE);

	        String simID = tm.getSimSerialNumber();
	        if (simID != null)
		        simno.setText(simID);
        }
    }

    public void save(View v)
    {
        String s=simno.getText().toString();

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(simcard.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("simno", s);

        editor.commit();

        Intent i=new Intent(simcard.this,home.class);
        startActivity(i);
        finish();

    }

}
