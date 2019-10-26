package com.example.mydramas.repository.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DramaRetrofitManager {
    //==============================
    // Static Fields
    //==============================
    private static DramaRetrofitManager sInstance;

    //==============================
    // Static Methods
    //==============================
    public static DramaRetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (DramaRetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new DramaRetrofitManager();
                }
            }
        }
        return sInstance;
    }

    //==============================
    // Member Fields
    //==============================
    DramaRemoteService mDramaRemoteService;

    //==============================
    // Constructors
    //==============================
    private DramaRetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://static.linetv.tw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mDramaRemoteService = retrofit.create(DramaRemoteService.class);
    }

    public DramaRemoteService getDramaRemoteService() {
        return mDramaRemoteService;
    }
}
