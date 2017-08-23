package com.example.hi.smartlocator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class account extends AppCompatActivity {

    EditText uname,oldpass,newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        uname= (EditText) findViewById(R.id.uname);
        oldpass= (EditText) findViewById(R.id.oldpass);
        newpass= (EditText) findViewById(R.id.newpass);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String s= app_preferences.getString("uname","");
        uname.setText(s);
    }

    public void saveandcontinue(View v)
    {
        String u=uname.getText().toString();
        String o=oldpass.getText().toString();
        String n=newpass.getText().toString();

        if(!u.equals(""))
        {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String s= app_preferences.getString("pass","");

            if((s.equals(o)) && (!n.equals("")))
            {
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString("uname", u);
                editor.putString("pass", n);
                editor.commit();
                Toast.makeText(account.this,"Password changed",Toast.LENGTH_SHORT).show();

                Intent i=new Intent(account.this,MainActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(account.this,"Enter correct values",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(account.this,"Enter username",Toast.LENGTH_SHORT).show();
    }
}
