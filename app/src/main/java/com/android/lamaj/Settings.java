package com.android.lamaj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    protected void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
        setContentView(R.layout.settings);
    }

    public void CancelSettings(View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    public void SaveSettings(View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
