package com.example.mydramas.viewmodel;

import android.app.Application;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mydramas.BasicApp;
import com.example.mydramas.repository.DataRepository2;
import com.example.mydramas.repository.db.DramaEntity;

import java.util.List;

public class DramaListViewModel extends AndroidViewModel {
    //==============================
    // Member Fields
    //==============================
    private final DataRepository2 mRepository;

    //==============================
    // Constructors
    //==============================
    public DramaListViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((BasicApp)application).getRepository();
    }

    //==============================
    // Member Methods
    //==============================
    @NonNull
    @MainThread
    public LiveData<DramaEntity> postLoadDeepLinkDramaFromDB(final int dramaId) {
        return mRepository.postLoadDeepLinkDramaFromDB(dramaId);
    }

    @NonNull
    @MainThread
    public LiveData<List<DramaEntity>> postLoadDramasFromDB(final String keyword) {
        return mRepository.postLoadDramasFromDB(keyword);
    }

    @NonNull
    @MainThread
    public LiveData<Long> postLoadDramasFromServer() {
        return mRepository.postLoadDramasFromServer();
    }
}
