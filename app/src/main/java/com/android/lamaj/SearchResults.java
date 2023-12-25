package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchResults extends AppCompatActivity {

    CheckBox Ipsrc;
    CheckBox Ipdst;
    CheckBox Protocol;
    EditText Search;
    LinearLayout SearchGroup;
    Button ButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ButtonBack = findViewById(R.id.back);
        Ipsrc = findViewById(R.id.SearchIpSrc);
        Ipdst = findViewById(R.id.SearchIpDst);
        Protocol = findViewById(R.id.SearchProtocol);
        Search = findViewById(R.id.SearchBar);
        SearchGroup = findViewById(R.id.SearchGroup);

        EditText editText = findViewById(R.id.SearchBar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editText.requestFocus();

        Ipsrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCheckBoxSelection(Ipsrc);
                updateEditText("Search per IP Src");
            }
        });

        Ipdst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCheckBoxSelection(Ipdst);
                updateEditText("Search per IP Dst");
            }
        });

        Protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCheckBoxSelection(Protocol);
                updateEditText("Search per Protocol");
            }
        });

        ButtonBack.setOnClickListener(view -> finish());

    }

    private void handleCheckBoxSelection(CheckBox checkBox) {
        if (checkBox == Ipsrc) {
            Ipdst.setChecked(false);
            Protocol.setChecked(false);
        } else if (checkBox == Ipdst) {
            Ipsrc.setChecked(false);
            Protocol.setChecked(false);
        } else if (checkBox == Protocol) {
            Ipsrc.setChecked(false);
            Ipdst.setChecked(false);
        }
    }

    private void updateEditText(String text) {
        Search.setHint(text);
    }
}