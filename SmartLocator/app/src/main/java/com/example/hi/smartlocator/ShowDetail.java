package com.example.hi.smartlocator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowDetail extends AppCompatActivity {

    EditText name,phone;
    Friends ob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        name= (EditText) findViewById(R.id.editname);
        phone= (EditText) findViewById(R.id.editphone);

        Bundle b=getIntent().getExtras();
        int pos=b.getInt("pos");
        ArrayList<Friends> list=b.getParcelableArrayList("list");
        ob=list.get(pos);

        name.setText(ob.getName());
        phone.setText(ob.getPhone());

    }

    public void update(View v)
    {
        String n=name.getText().toString();
        String ph=phone.getText().toString();

        if(n.equals("") || ph.equals(""))
            Toast.makeText(ShowDetail.this,"Enter proper values",Toast.LENGTH_SHORT).show();
        else
        {
            DBAdapter db=new DBAdapter(ShowDetail.this);
            db.open();
            if(db.updateFriend(ob.getId(),n,ph))
                Toast.makeText(ShowDetail.this,"updated",Toast.LENGTH_SHORT).show();
            db.close();



            Intent i=new Intent(ShowDetail.this,home.class);
            startActivity(i);
            finish();
        }
    }

    public void delete(View v)
    {
        DBAdapter db=new DBAdapter(ShowDetail.this);
        db.open();
        if(db.deleteFriend(ob.getId()))
            Toast.makeText(ShowDetail.this,"deleted",Toast.LENGTH_SHORT).show();
        db.close();

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(ShowDetail.this);
        int s = app_preferences.getInt("remainingfriend", 5);

        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("remainingfriend", s+1);

        editor.commit();

        Intent i=new Intent(ShowDetail.this,home.class);
        startActivity(i);
        finish();
    }
}
