package com.abiyedanagogo.invasion;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

/*
 * Created by Abiye Danagogo on 17/05/2020.
 * The MusicPlayerService is responsible for playing the music while the application is running
 * */
public class MusicPlayerService extends Service {
    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.gamemusic);
        player.setLooping(true);
        player.setVolume(10,10);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

}
