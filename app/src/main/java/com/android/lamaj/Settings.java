package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class Settings extends AppCompatActivity {

    CheckBox checkLightMode;
    Button buttonBack;
    CheckBox checkDarkMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkLightMode = findViewById(R.id.check_light_mode);
        buttonBack = findViewById(R.id.back);
        checkDarkMode = findViewById(R.id.check_dark_mode);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        checkLightMode.setChecked(currentNightMode != Configuration.UI_MODE_NIGHT_YES);
        checkDarkMode.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        checkLightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkDarkMode.setChecked(false);
            } else {
                checkDarkMode.setChecked(true);
            }
            applyTheme();
        });

        checkDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkLightMode.setChecked(false);
            } else {
                checkLightMode.setChecked(true);
            }
            applyTheme();
        });

        buttonBack.setOnClickListener(view -> finish());

    }
    private void applyTheme() {
        if (checkDarkMode.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        recreate();
    }
}