package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchFrames extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_frames);

    }

    public void OpenSettings(View view) {
        DialogUse.OpenSettings(this, view);
    }

    public void OpenImport(View view) {
        DialogUse.OpenImport(this, view);
    }

}