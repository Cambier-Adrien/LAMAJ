package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

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

    public void OpenRemove(View view) {
        DialogUse.OpenRemove(this, view);
    }

    public void OpenSearch(View view) {
        DialogUse.OpenSearch(this, view);
    }


}