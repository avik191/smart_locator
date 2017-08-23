package com.example.hi.smartlocator;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class home extends AppCompatActivity{

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GPSTracker gps = new GPSTracker(this);



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
            Intent i=new Intent(home.this,account.class);
            startActivity(i);
        }
        if (id == R.id.contact) {
            Intent i=new Intent(home.this,contact.class);
            startActivity(i);
        }
        if (id == R.id.about) {
            final Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    final Dialog dialog = new Dialog(home.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.about_dialog_box);
                    dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.show();
                    Button okBtn = (Button) dialog.findViewById(R.id.okBtn);
                    okBtn.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            dialog.dismiss();

                        }
                    });
                }
            };
            new Handler(getMainLooper()).post(r);

        }
        if (id == R.id.exit) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void addfriend(View v) {
        Intent i = new Intent(home.this, addfriend.class);
        startActivity(i);
        //finish();
    }

    public void friendlist(View v) {
        Intent i = new Intent(home.this, friendlist.class);
        startActivity(i);
        //finish();
    }

    public void simcard(View v) {
        Intent i = new Intent(home.this, simcard.class);
        startActivity(i);
    }


}
