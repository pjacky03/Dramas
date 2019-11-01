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
    // Put message to background thread, get data from local database, and then update live data.
    // Return the live data of drama. Update the live data if find the drama with dramaId.
    @NonNull
    @MainThread
    public LiveData<DramaEntity> postLoadDeepLinkDramaFromDB(final int dramaId) {
        return mRepository.postLoadDeepLinkDramaFromDB(dramaId);
    }

    // Put message to background thread, get data from local database, and then update live datas.
    // Return the live data of drama list. Update live data if find the drama list in which the
    // name of each dramas include keyword.
    @NonNull
    @MainThread
    public LiveData<List<DramaEntity>> postLoadDramasFromDB(final String keyword) {
        return mRepository.postLoadDramasFromDB(keyword);
    }

    // Put message to background thread, get data from server, and then update live data.
    // Return the live data of the updating time of network data. Update the live data after
    // querying data from network.
    @NonNull
    @MainThread
    public LiveData<Long> postLoadDramasFromServer() {
        return mRepository.postLoadDramasFromServer();
    }
}
