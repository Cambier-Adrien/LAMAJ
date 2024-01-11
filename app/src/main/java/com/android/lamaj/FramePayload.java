package com.android.lamaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FramePayload extends AppCompatActivity {

    Button buttonBack;
    TextView textSrc;
    TextView textDst;
    TextView textProtocol;
    TextView textPayload;
    Button copyButtonSrc;
    Button copyButtonDst;
    Button copyButtonProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_payload);

        buttonBack = findViewById(R.id.back);
        textSrc = findViewById(R.id.textSrc);
        textDst = findViewById(R.id.textDst);
        textProtocol = findViewById(R.id.textProtocol);
        textPayload = findViewById(R.id.textPayload);
        copyButtonSrc = findViewById(R.id.copyButtonSrc);
        copyButtonDst = findViewById(R.id.copyButtonDst);
        copyButtonProtocol = findViewById(R.id.copyButtonProtocol);

        Intent intent = getIntent();
        String ipSrc = intent.getStringExtra("IPSrc");
        String ipDst = intent.getStringExtra("IPDst");
        String protocol = intent.getStringExtra("Protocol");
        String payload = intent.getStringExtra("Payload");

        textSrc.setText(ipSrc);
        textDst.setText(ipDst);
        textProtocol.setText(protocol);
        textPayload.setText(payload);

        copyButtonSrc.setOnClickListener(view -> {
            copyToClipboard(textSrc.getText().toString());
        });

        copyButtonDst.setOnClickListener(view -> {
            copyToClipboard(textDst.getText().toString());
        });

        copyButtonProtocol.setOnClickListener(view -> {
            copyToClipboard(textProtocol.getText().toString());
        });

        buttonBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void copyToClipboard(String textToCopy) {
        Toast.makeText(this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
    }
}