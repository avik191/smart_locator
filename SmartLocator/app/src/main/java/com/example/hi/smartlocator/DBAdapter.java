package com.example.hi.smartlocator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by HI on 26-Feb-17.
 */

public class DBAdapter {

    static final String DBNAME="List";
    static final String TABLENAME="friend_list";
    static final String ID="id";
    static final String NAME="name";
    static final String PHONE="phone";
    static final int DBVERSION=1;
    static final String DBCreate="create table friend_list (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name text not null,phone text not null);";


    Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context) {
            super(context,DBNAME,null,DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(DBCreate);
                Toast.makeText(context,"db created",Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS friend_list");
            onCreate(sqLiteDatabase);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        //  DBHelper = new DatabaseHelper(context);
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    public long insertFriend(String name,String phone)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, name);
        initialValues.put(PHONE,phone);
        return db.insert(TABLENAME, null, initialValues);
    }

    //---deletes a particular reminder---
    public boolean deleteFriend(long rowId)
    {
        return db.delete(TABLENAME, ID + "=" + rowId, null) > 0;
    }

    //---retrieves all the reminders---
    public Cursor getAllFriends()
    {
        return db.query(TABLENAME, new String[] {ID,NAME,PHONE}, null, null, null, null, null);
    }
    //---retrieves a particular reminder---
    public Cursor getFriendId(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, TABLENAME,  new String[] {ID,NAME,PHONE},ID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateFriend(long rowId,String name,String phone)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, name);
        initialValues.put(PHONE,phone);
        return db.update(
                TABLENAME, initialValues, ID + "=" + rowId, null) > 0;
    }

}
