package com.abiyedanagogo.invasion;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

/*
 * Created by Abiye Danagogo on 17/05/2020.
 * This class that implements the lifecycleObserver was made because the game music kept playing even when the app was in
 * the background and only stopped when the app was closed. Now with the this class it checks what state the app is in and depending
 * on that it starts or stops the MusicPlayerService which is responsible for playing the music.
 * */

public class LifecycleObserverClass extends Application implements LifecycleObserver {
    SharedPreferences preferences;
    boolean musicToggle;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        stopService(new Intent(this, MusicPlayerService.class));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForeGrounded() {
        preferences = getSharedPreferences("game", MODE_PRIVATE);
        musicToggle = preferences.getBoolean("music", true);

        if (musicToggle) {
            startService(new Intent(this, MusicPlayerService.class));
        }
    }

}
