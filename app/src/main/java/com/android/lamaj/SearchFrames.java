package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFrames extends AppCompatActivity implements DialogUse.OnImportCompleteListener{

    RecyclerView recyclerView;
    FrameDB frameDB;
    FrameListAdapter frameListAdapter;
    ProgressBar progressBar;
    private static final String PREF_ICMP_CHECKED = "isICMPChecked";
    private static final String PREF_TCP_CHECKED = "isTCPChecked";
    private static final String PREF_UDP_CHECKED = "isUDPChecked";
    private static final String PREF_HTTP_CHECKED = "isHTTPChecked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_frames);

        recyclerView = findViewById(R.id.frameList);
        progressBar = findViewById(R.id.progressBar);
        frameDB = FrameDB.getInstance(this);
        frameListAdapter = new FrameListAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(frameListAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("my_app_settings", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener((preferences, key) -> {
            updateRecyclerView();
        });

        showProgressBar();
        updateRecyclerView();

        int whichPage = sharedPreferences.getInt("whichPage", 1);
        DialogUse.importFrames(SearchFrames.this, frameDB, this, whichPage);
    }

    public void OpenSettings(View view) {
        DialogUse.OpenSettings(this, view);
    }

    public void OpenChange(View view) {
        DialogUse.OpenChange(this, view);
    }

    public void OpenRemove(View view) {
        DialogUse.OpenRemove(this, view);
    }

    public void OpenSearch(View view) {
        DialogUse.OpenSearch(this, view);
    }

    public void updateRecyclerView() {
        DialogUse.updateRecyclerView(this, frameDB, frameListAdapter);
    }

    @Override
    public void onImportComplete() {
        updateRecyclerView();

        hideProgressBar();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}