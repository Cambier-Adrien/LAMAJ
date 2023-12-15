package com.android.lamaj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class DialogUse {
    public static void OpenSettings(AppCompatActivity activity, View view){
        Dialog DialogSettings = new Dialog(activity);
        DialogSettings.setContentView(R.layout.settings);

        Switch switchLightMode;
        Switch switchDarkMode;
        Button Cancel;
        Button Save;

        switchLightMode =(Switch) DialogSettings.findViewById(R.id.switch_light_mode);
        switchDarkMode =(Switch) DialogSettings.findViewById(R.id.switch_dark_mode);
        Cancel =(Button) DialogSettings.findViewById(R.id.Cancel);
        Save =(Button) DialogSettings.findViewById(R.id.Save);

        Cancel.setOnClickListener(v -> DialogSettings.dismiss());

        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switchLightMode.setChecked(currentNightMode != Configuration.UI_MODE_NIGHT_YES);
        switchDarkMode.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        Save.setOnClickListener(v -> {
            if (switchLightMode.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (switchDarkMode.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            activity.getDelegate().applyDayNight();
            DialogSettings.dismiss();
        });

        switchLightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchDarkMode.setChecked(false);
            } else {
                switchDarkMode.setChecked(true);
            }
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchLightMode.setChecked(false);
            } else {
                switchLightMode.setChecked(true);
            }
        });

        if (DialogSettings.getWindow() != null){
            DialogSettings.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        DialogSettings.show();
    }

    public static void OpenImport(AppCompatActivity activity,View view){
        Dialog DialogImport = new Dialog(activity);

        Button Cancel;
        Button Save;
        DialogImport.setContentView(R.layout.import_datas);
        Cancel =(Button) DialogImport.findViewById(R.id.Cancel);
        Save =(Button) DialogImport.findViewById(R.id.Save);
        Cancel.setOnClickListener(v -> DialogImport.dismiss());
        if (DialogImport.getWindow() != null){
            DialogImport.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        DialogImport.show();
        Save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SearchFrames.class);
                activity.startActivity(intent);
            }
        });
    }
}
