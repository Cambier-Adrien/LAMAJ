package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SearchFrames extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameDB frameDB;
    private FrameListAdapter frameListAdapter;

    // Constantes pour les clés des préférences
    private static final String PREF_ICMP_CHECKED = "isICMPChecked";
    private static final String PREF_TCP_CHECKED = "isTCPChecked";
    private static final String PREF_UDP_CHECKED = "isUDPChecked";
    private static final String PREF_HTTP_CHECKED = "isHTTPChecked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_frames);

        recyclerView = findViewById(R.id.frameList);
        frameDB = FrameDB.getInstance(this);
        frameListAdapter = new FrameListAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(frameListAdapter);

        updateRecyclerView();

        SharedPreferences sharedPreferences = getSharedPreferences("my_app_settings", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener((preferences, key) -> {
            if (key.equals(PREF_ICMP_CHECKED) || key.equals(PREF_TCP_CHECKED) || key.equals(PREF_UDP_CHECKED) || key.equals(PREF_HTTP_CHECKED)) {
                updateRecyclerView();
            }
        });
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

    private void updateRecyclerView() {
        DialogUse.updateRecyclerView(this, frameDB, recyclerView, frameListAdapter);
    }
}