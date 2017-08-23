package com.example.hi.smartlocator;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class friendlist extends AppCompatActivity {

    DBAdapter db;
    RecyclerView recycle;
    MyCustomAdapter adapter;
    ArrayList<Friends> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int isEmpty      = app_preferences.getInt("isempty", 0);
        if(isEmpty==0)
            setContentView(R.layout.activity_friendlist2);
        else
        {
            setContentView(R.layout.activity_friendlist);

            recycle = (RecyclerView) findViewById(R.id.recycleview);
            db = new DBAdapter(friendlist.this);
            list = new ArrayList<Friends>();

            db.open();
            Cursor c = db.getAllFriends();
            if (c != null)
            {
                if (c.moveToFirst())
                {
                    do
                    {
                        Friends ob = new Friends();
                        ob.setId(c.getInt(0));
                        ob.setName(c.getString(1));
                        ob.setPhone(c.getString(2));

                        list.add(ob);

                    } while (c.moveToNext());
                }
                db.close();
            }

            if(list.size()==0)
	            setContentView(R.layout.activity_friendlist2);
			else
            {
	            recycle.setLayoutManager(new LinearLayoutManager(this));

	            adapter = new MyCustomAdapter(this, list);
	            recycle.setAdapter(adapter);
            }
        }
    }
}
