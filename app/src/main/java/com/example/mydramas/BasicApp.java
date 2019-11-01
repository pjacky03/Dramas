package com.example.mydramas;

import android.app.Application;

import com.example.mydramas.repository.DataRepository2;
import com.example.mydramas.repository.db.AppDatabase;

public class BasicApp extends Application {
    //==============================
    // Member Fields
    //==============================
    AppExecutors mAppExecutors;

    //==============================
    // Member Methods
    //==============================
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize app executors for multi-threading
        mAppExecutors = new AppExecutors();
    }

    // SQLite DB to store drama information.
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    // Data repository to query data from db or update data from server.
    public DataRepository2 getRepository() {
        return DataRepository2.getInstance(getDatabase(), mAppExecutors);
    }
}
