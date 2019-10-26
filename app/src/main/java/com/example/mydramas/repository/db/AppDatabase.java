package com.example.mydramas.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DramaEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //==============================
    // Static Fields
    //==============================
    public static final String DATABASE_NAME = "dramas-db";

    private static AppDatabase sInstance;

    //==============================
    // Static Methods
    //==============================
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    //==============================
    // Member Methods
    //==============================
    public abstract DramaDao dramaDao();
}
