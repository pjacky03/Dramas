package com.example.mydramas.repository;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mydramas.AppExecutors;
import com.example.mydramas.repository.db.AppDatabase;
import com.example.mydramas.repository.db.DramaEntity;
import com.example.mydramas.repository.network.DramaRemote;
import com.example.mydramas.repository.network.DramaRemoteWrapper;
import com.example.mydramas.repository.network.DramaRetrofitManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DataRepository2 {
    //==============================
    // Static Fields
    //==============================
    private static final String TAG = "DataRepository2";

    private static DataRepository2 sInstance;

    //==============================
    // Static Methods
    //==============================
    public static DataRepository2 getInstance(final AppDatabase database, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataRepository2.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository2(database, executors);
                }
            }
        }
        return sInstance;
    }

    //==============================
    // Member Fields
    //==============================
    private final AppDatabase mDatabase;
    private final AppExecutors mAppExecutors;

    private MutableLiveData<List<DramaEntity>> mObservableDramas;
    private MutableLiveData<Long> mObservableNetworkDataUpdateTime;

    private MutableLiveData<DramaEntity> mObservableDeepLinkDrama;

    //==============================
    // Constructors
    //==============================
    private DataRepository2(final AppDatabase database, final AppExecutors executors) {
        mDatabase = database;
        mAppExecutors = executors;

        mObservableDramas = new MutableLiveData<List<DramaEntity>>();
        mObservableNetworkDataUpdateTime = new MutableLiveData<Long>();
        mObservableDeepLinkDrama = new MutableLiveData<DramaEntity>();
    }

    //==============================
    // Member Methods
    //==============================
    public LiveData<List<DramaEntity>> getDramas() {
        return mObservableDramas;
    }

    public LiveData<List<DramaEntity>> postLoadDramasFromDB(final String keyword) {
        mAppExecutors.diskIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        List<DramaEntity> dramas = TextUtils.isEmpty(keyword)
                                ? mDatabase.dramaDao().loadAllDramas()
                                : mDatabase.dramaDao().loadDramasWithKeyword(keyword);
                        mObservableDramas.postValue(dramas);
                    }
                }
        );
        return mObservableDramas;
    }

    public MutableLiveData<Long> postLoadDramasFromServer() {
        mAppExecutors.networkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        // Get remote data from https://static.linetv.tw/interview/dramas-sample.json
                        Call<DramaRemoteWrapper> call = DramaRetrofitManager.getInstance().getDramaRemoteService().loadDramas();
                        try {
                            Response<DramaRemoteWrapper> response = call.execute();
                            if (response.isSuccessful()) {
                                Log.d(TAG, "LoadDramasFromServerWorker: Connection Success");

                                List<DramaRemote> dramaRemotes = response.body().getDramaRemote();
                                List<DramaEntity> dramaEntities = new ArrayList<DramaEntity>();
                                DataConverter.DramaRemoteToDramaEntity(dramaRemotes, dramaEntities);
                                mDatabase.dramaDao().insertAll(dramaEntities);
                                mObservableNetworkDataUpdateTime.postValue(System.currentTimeMillis());
                            } else {
                                Log.w(TAG, "LoadDramasFromServerWorker: Connection Fail");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        return mObservableNetworkDataUpdateTime;
    }

    public LiveData<DramaEntity> postLoadDeepLinkDramaFromDB(final int dramaId) {
        mAppExecutors.diskIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        List<DramaEntity> dramas = mDatabase.dramaDao().loadDramasWithDramaId(dramaId);
                        if (dramas.size() > 0) {
                            Log.w(TAG, "LoadDeepLinkDramaFromDB: dramaId="+dramas.get(0).dramaId);

                            mObservableDeepLinkDrama.postValue(dramas.get(0));
                        }
                    }
                }
        );
        return mObservableDeepLinkDrama;
    }
}
