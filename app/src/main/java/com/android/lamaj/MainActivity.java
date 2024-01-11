package com.android.lamaj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameDB frameDB;
    private List<FrameWireshark> frames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("my_app_settings", MODE_PRIVATE);
        File databaseFile = getDatabasePath("Frame.db");

        if (savedInstanceState == null) {
            if (sharedPreferences.getBoolean("isDarkMode", true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (sharedPreferences.getBoolean("isLightMode", true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();

            boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);

            if (isFirstLaunch) {
                editor.putBoolean("isICMPChecked", true);
                editor.putBoolean("isTCPChecked", true);
                editor.putBoolean("isUDPChecked", true);
                editor.putBoolean("isHTTPChecked", true);
                editor.putBoolean("isFirstLaunch", false);
                editor.apply();
            }

            frameDB = new FrameDB(this);

            if (!databaseFile.exists()) {
                FrameDBHelper frameDBHelper = new FrameDBHelper(this);
                frameDBHelper.onCreate(frameDBHelper.getWritableDatabase());
            }

            frames = frameDB.getAllFrames();

            if (frames != null && frames.size() > 0) {
                Intent intent = new Intent(this, SearchFrames.class);
                startActivity(intent);
            }
        }
    }

    public void OpenSettings(View view) {
        DialogUse.OpenSettings(this, view);
    }

    public void OpenImport(View view) {
        DialogUse.OpenImport(this, view);
    }
}