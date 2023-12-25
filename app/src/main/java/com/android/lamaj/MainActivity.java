package com.android.lamaj;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenSettings(View view) {
        DialogUse.OpenSettings(this, view);
    }
    public void OpenImport(View view) {
        DialogUse.OpenImport(this, view);
    }
}