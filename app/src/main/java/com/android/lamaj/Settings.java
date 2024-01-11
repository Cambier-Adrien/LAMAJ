package com.android.lamaj;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;

public class Settings extends AppCompatActivity {

    CheckBox checkLightMode;
    Button buttonBack;
    Button addButton;
    CheckBox checkDarkMode;
    CheckBox checkICMP;
    CheckBox checkTCP;
    CheckBox checkUDP;
    CheckBox checkHTTP;

    EditText page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkLightMode = findViewById(R.id.check_light_mode);
        buttonBack = findViewById(R.id.back);
        checkDarkMode = findViewById(R.id.check_dark_mode);
        checkICMP = findViewById(R.id.checkICMP);
        checkTCP = findViewById(R.id.checkTCP);
        checkUDP = findViewById(R.id.checkUDP);
        checkHTTP = findViewById(R.id.checkHTTP);
        page = findViewById(R.id.textPage);
        addButton = findViewById(R.id.addButton);

        SharedPreferences sharedPreferences = getSharedPreferences("my_app_settings", MODE_PRIVATE);

        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        boolean isLightMode = sharedPreferences.getBoolean("isLightMode", true);
        boolean isICMPChecked = sharedPreferences.getBoolean("isICMPChecked", true);
        boolean isTCPChecked = sharedPreferences.getBoolean("isTCPChecked", true);
        boolean isUDPChecked = sharedPreferences.getBoolean("isUDPChecked", true);
        boolean isHTTPChecked = sharedPreferences.getBoolean("isHTTPChecked", true);
        int whichPage = sharedPreferences.getInt("whichPage", 1);

        checkDarkMode.setChecked(isDarkMode);
        checkLightMode.setChecked(isLightMode);
        checkICMP.setChecked(isICMPChecked);
        checkTCP.setChecked(isTCPChecked);
        checkUDP.setChecked(isUDPChecked);
        checkHTTP.setChecked(isHTTPChecked);
        page.setText(String.valueOf(whichPage));

        SharedPreferences.Editor editor = sharedPreferences.edit();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentWhichPage = sharedPreferences.getInt("whichPage", 1);

                int newWhichPage = currentWhichPage + 1;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("whichPage", newWhichPage);
                editor.apply();

                page.setText(String.valueOf(newWhichPage));
            }
        });

        checkICMP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("isICMPChecked", isChecked);
        });

        checkTCP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("isTCPChecked", isChecked);
        });

        checkUDP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("isUDPChecked", isChecked);
        });

        checkHTTP.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("isHTTPChecked", isChecked);
        });

        checkLightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkDarkMode.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("isDarkMode", false);
                editor.putBoolean("isLightMode", true);
            } else {
                checkDarkMode.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("isDarkMode", true);
                editor.putBoolean("isLightMode", false);
            }
        });

        checkDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkLightMode.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("isDarkMode", true);
                editor.putBoolean("isLightMode", false);
            } else {
                checkLightMode.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("isDarkMode", false);
                editor.putBoolean("isLightMode", true);

            }
        });

        buttonBack.setOnClickListener(view -> {
            String pageValue = page.getText().toString();

            if (!pageValue.isEmpty() && !pageValue.equals("0")) {
                int pageNumber = Integer.parseInt(pageValue);

                editor.putInt("whichPage", pageNumber);
                editor.apply();

                finish();
            } else {
                page.setError("Please enter a valid number");
            }
        });
    }

}