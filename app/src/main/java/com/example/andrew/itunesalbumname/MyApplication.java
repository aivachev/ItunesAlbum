package com.example.andrew.itunesalbumname;

import android.app.Application;

import androidx.room.Room;

import com.example.andrew.itunesalbumname.db.database.MyDatabase;

public class MyApplication extends Application {

    public static MyApplication instance;
    MyDatabase myDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myDatabase = Room.databaseBuilder(this, MyDatabase.class, MyDatabase.NAME).fallbackToDestructiveMigration().build();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public MyDatabase getMyDatabase() {
        return myDatabase;
    }
}
