package com.example.hi.smartlocator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class contact extends AppCompatActivity {

    EditText phone;
    String ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        phone= (EditText) findViewById(R.id.simno);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ph = app_preferences.getString("loc_phone","");

        phone.setText(ph);
    }

    public void save(View v)
    {
        String s=phone.getText().toString();
        if(!s.equals(""))
        {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(contact.this);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("loc_phone", s);

            editor.commit();
            Toast.makeText(contact.this,"Number saved",Toast.LENGTH_SHORT).show();

            Intent i=new Intent(contact.this,home.class);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(contact.this,"Please enter a number",Toast.LENGTH_SHORT).show();
        }
    }
}
