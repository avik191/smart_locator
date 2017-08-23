package com.example.hi.smartlocator;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addfriend extends AppCompatActivity {

    EditText name,phone;
    TextView rem,loc,ring,play;
    int remaining=0;
    SharedPreferences app_preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        if ((ContextCompat.checkSelfPermission(addfriend.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(addfriend.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(addfriend.this,
		        Manifest.permission.SEND_SMS)
		        != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(addfriend.this,
		        Manifest.permission.ACCESS_COARSE_LOCATION)
		        != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(addfriend.this,
		        Manifest.permission.ACCESS_FINE_LOCATION)
		        != PackageManager.PERMISSION_GRANTED)|| (ContextCompat.checkSelfPermission(addfriend.this,
		        Manifest.permission.WAKE_LOCK)
		        != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(addfriend.this,
                    new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.SEND_SMS
		                    ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
		                    Manifest.permission.WAKE_LOCK},10);

        }

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phone);

        rem= (TextView) findViewById(R.id.remfriend);
        loc= (TextView) findViewById(R.id.loc_code);
        ring= (TextView) findViewById(R.id.ring_code);
	    play= (TextView) findViewById(R.id.startring_code);

	    remaining = app_preferences.getInt("remainingfriend", 5);
        rem.setText(""+remaining);

        String uname=app_preferences.getString("uname","");
        loc.setText(uname+"_locate");
        ring.setText(uname+"_ring");
	    play.setText(uname+"_startring");
    }

    public void add(View v)
    {
        if(remaining>0)
        {
            String n = name.getText().toString();
            String ph = phone.getText().toString();

            if (n.equals("") || ph.equals(""))
                Toast.makeText(addfriend.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            else {
                DBAdapter db = new DBAdapter(addfriend.this);
                try {
                    db.open();

                    if ((db.insertFriend(n, ph)) > 0)
                        Toast.makeText(addfriend.this, "inserted", Toast.LENGTH_SHORT).show();

                    db.close();
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putInt("remainingfriend", remaining - 1);
                    editor.putInt("isempty",5);
                    editor.commit();

                    Intent i = new Intent(addfriend.this, home.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(addfriend.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
            Toast.makeText(addfriend.this,"Only 5 friends are allowed",Toast.LENGTH_SHORT).show();
    }


}
