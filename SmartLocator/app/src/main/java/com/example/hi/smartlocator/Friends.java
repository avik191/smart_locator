package com.example.hi.smartlocator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HI on 26-Feb-17.
 */

public class Friends implements Parcelable{
    String name;
    String phone;
    int id;

    public Friends() {
    }

    protected Friends(Parcel in) {
        name = in.readString();
        phone = in.readString();
        id = in.readInt();
    }

    public static final Creator<Friends> CREATOR = new Creator<Friends>() {
        @Override
        public Friends createFromParcel(Parcel in) {
            return new Friends(in);
        }

        @Override
        public Friends[] newArray(int size) {
            return new Friends[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeInt(id);
    }
}
