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

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository2 getRepository() {
        return DataRepository2.getInstance(getDatabase(), mAppExecutors);
    }
}
