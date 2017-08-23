package com.example.hi.smartlocator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText uname,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int check      = app_preferences.getInt("check", 1);

        if(check==1)
            setContentView(R.layout.activity_main);
        else if(check == 2)
            setContentView(R.layout.activity_main2);
        else if(check == 3)
        {
	        Intent i=new Intent(MainActivity.this,home.class);
	        startActivity(i);
	        finish();
        }


        uname= (EditText) findViewById(R.id.uname);
        pass= (EditText) findViewById(R.id.pass);
    }

    public void saveandcontinue(View v)
    {
        String u=uname.getText().toString();
        String p=pass.getText().toString();

        if(u.equals("") && p.equals("")) {
            Toast.makeText(MainActivity.this, "wrong input", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("uname", u);
            editor.putString("pass", p);
            editor.putInt("check", 3);

            editor.commit();

            Toast.makeText(MainActivity.this, "account set", Toast.LENGTH_SHORT).show();

            Intent i=new Intent(MainActivity.this,home.class);
            startActivity(i);
            finish();
        }
    }

    public void login(View v)
    {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String u=uname.getText().toString();
        String p=pass.getText().toString();

        String un=app_preferences.getString("uname","");
        String ps=app_preferences.getString("pass","");

        if((u.equals(un)) &&(p.equals(ps))){
            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();


            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("check", 3);

            editor.commit();

            Intent i=new Intent(MainActivity.this,home.class);
            startActivity(i);
            finish();
        }
        else
            Toast.makeText(MainActivity.this,"wrong input",Toast.LENGTH_SHORT).show();

    }
}
