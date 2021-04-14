package com.example.notepad.db;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor instance;
    private final Executor mainID;
    private final Executor subID;

    public AppExecutor(Executor mainID, Executor subID) {
        this.mainID = mainID;
        this.subID = subID;
    }

    public static AppExecutor getInstance(){

        if(instance == null) instance = new AppExecutor(new MainThreadHandler(), Executors.newSingleThreadExecutor());
        return instance;
    }

    public static class MainThreadHandler implements Executor{
    private Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainHandler.post(command);
        }
    }

    public Executor getMainID() {
        return mainID;
    }

    public Executor getSubID() {
        return subID;
    }
}
