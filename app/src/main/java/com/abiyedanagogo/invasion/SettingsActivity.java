package com.abiyedanagogo.invasion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Created by Abiye Danagogo on 17/05/2020.
 * This activity displays the settings of the game
 * */

public class SettingsActivity extends AppCompatActivity {

    private boolean soundToggle;
    private boolean musicToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_settings);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        soundToggle = prefs.getBoolean("sound", true);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (soundToggle){
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
        else {
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundToggle = !soundToggle;
                if (soundToggle){
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }
                else {
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("sound", soundToggle);
                editor.apply();
            }
        });

        musicToggle = prefs.getBoolean("music", true);
        final ImageView musicCtrl = findViewById(R.id.musicCtrl);

        if (musicToggle){
            musicCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }else {
            musicCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }


        musicCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicToggle = !musicToggle;

                if (musicToggle){
                    musicCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
                    startService(new Intent(SettingsActivity.this, MusicPlayerService.class));
                }
                else {
                    musicCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                    stopService(new Intent(SettingsActivity.this, MusicPlayerService.class));
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("music", musicToggle);
                editor.apply();

            }
        });


        TextView creditsText = findViewById(R.id.creditsText);
        creditsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, CreditsActivity.class));
            }
        });

        TextView backSettingsText = findViewById(R.id.backSettingsText);
        backSettingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /*
    * This stops the navigation buttons from displaying to improve the full screen experience
    * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
