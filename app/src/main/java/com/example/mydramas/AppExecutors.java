package com.example.mydramas;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    //==============================
    // Type
    //==============================
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    //==============================
    // Member Fields
    //==============================
    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    //==============================
    // Constructors
    //==============================
    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor()
                , Executors.newFixedThreadPool(1)
                , new MainThreadExecutor());
    }

    //==============================
    // Member Methods
    //==============================
    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }
}
