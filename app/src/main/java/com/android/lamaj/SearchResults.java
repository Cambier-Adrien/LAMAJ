package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    Button ButtonBack;
    EditText Search;
    CheckBox Ipsrc;
    CheckBox Ipdst;
    CheckBox Protocol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ButtonBack = findViewById(R.id.back);
        Search = findViewById(R.id.SearchBar);
        Ipsrc = findViewById(R.id.SearchIpSrc);
        Ipdst = findViewById(R.id.SearchIpDst);
        Protocol = findViewById(R.id.SearchProtocol);

        Ipsrc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Ipdst.setChecked(false);
                Protocol.setChecked(false);
                Search.setHint("Search by Source IP");
            }
        });

        Ipdst.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Ipsrc.setChecked(false);
                Protocol.setChecked(false);
                Search.setHint("Search by Destination IP");
            }
        });

        Protocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Ipsrc.setChecked(false);
                Ipdst.setChecked(false);
                Search.setHint("Search by Protocol");
            }
        });

        ButtonBack.setOnClickListener(view -> finish());

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                handleCheckBoxSelection();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void handleCheckBoxSelection() {
        String searchText = Search.getText().toString();
        RecyclerView recyclerView = findViewById(R.id.frameList);

        if (searchText.length() > 0) {
            FrameListAdapter adapter = new FrameListAdapter(this, getFilteredFrames(searchText));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private List<FrameWireshark> getFilteredFrames(String searchText) {
        List<FrameWireshark> filteredFrames = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("my_app_settings", Context.MODE_PRIVATE);
        boolean isICMPChecked = sharedPreferences.getBoolean("isICMPChecked", true);
        boolean isTCPChecked = sharedPreferences.getBoolean("isTCPChecked", true);
        boolean isUDPChecked = sharedPreferences.getBoolean("isUDPChecked", true);
        boolean isHTTPChecked = sharedPreferences.getBoolean("isHTTPChecked", true);

        FrameDB frameDB = new FrameDB(getApplicationContext());
        List<FrameWireshark> frames = frameDB.getAllFramesFiltered(isICMPChecked, isTCPChecked, isUDPChecked, isHTTPChecked);

        for (FrameWireshark frame : frames) {
            String protocol = frame.getProtocol();
            String srcIp = frame.getSourceIP();
            String dstIp = frame.getDestinationIP();

            boolean isProtocolChecked = sharedPreferences.getBoolean(protocol, true);

            if ((Ipsrc.isChecked() && srcIp.equalsIgnoreCase(searchText)) ||
                    (Ipdst.isChecked() && dstIp.equalsIgnoreCase(searchText)) ||
                    (Protocol.isChecked() && protocol.equalsIgnoreCase(searchText))) {
                if (isProtocolChecked) {
                    filteredFrames.add(frame);
                }
            } else {
                if (checkFrameForSearch(frame, searchText, isProtocolChecked)) {
                    filteredFrames.add(frame);
                }
            }
        }

        return filteredFrames;
    }

    private boolean checkFrameForSearch(FrameWireshark frame, String searchText, boolean isProtocolChecked) {
        return (Ipsrc.isChecked() && frame.getSourceIP().contains(searchText) && isProtocolChecked) ||
                (Ipdst.isChecked() && frame.getDestinationIP().contains(searchText) && isProtocolChecked) ||
                (Protocol.isChecked() && frame.toString().contains(searchText) && isProtocolChecked) ||
                (!Ipsrc.isChecked() && !Ipdst.isChecked() && !Protocol.isChecked() && !searchText.isEmpty() && frame.toString().contains(searchText));
    }
}