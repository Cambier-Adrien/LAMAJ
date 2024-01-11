package com.android.lamaj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.codec.DecoderException;

import java.util.ArrayList;
import java.util.List;

public abstract class DialogUse extends Context {

    private static FrameListAdapter frameListAdapter;

    public static void OpenSettings(AppCompatActivity activity, View view) {
        Intent intent = new Intent(activity, Settings.class);
        activity.startActivity(intent);
    }

    public static void OpenSearch(AppCompatActivity activity, View view) {
        Intent intent = new Intent(activity, SearchResults.class);
        activity.startActivity(intent);
    }

    public static void OpenImport(AppCompatActivity activity, View view) {
        Dialog dialogImport = new Dialog(activity);
        FrameDB frameDB = new FrameDB(activity);

        Button cancel;
        Button save;

        dialogImport.setContentView(R.layout.import_data);
        cancel = dialogImport.findViewById(R.id.Cancel);
        save = dialogImport.findViewById(R.id.Save);

        Window window = dialogImport.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        cancel.setOnClickListener(v -> dialogImport.dismiss());
        dialogImport.setCanceledOnTouchOutside(false);
        dialogImport.show();

        save.setOnClickListener(view1 -> {
            frameDB.deleteAllFrames();
            Intent intent = new Intent(activity, SearchFrames.class);
            activity.startActivity(intent);
            dialogImport.dismiss();
        });
    }

    public static void OpenChange(AppCompatActivity activity, View view) {
        Dialog dialogChange = new Dialog(activity);
        FrameDB frameDB = new FrameDB(activity);

        Button cancel;
        Button save;

        dialogChange.setContentView(R.layout.change_data);
        cancel = dialogChange.findViewById(R.id.Cancel);
        save = dialogChange.findViewById(R.id.Save);

        Window window = dialogChange.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        cancel.setOnClickListener(v -> dialogChange.dismiss());
        dialogChange.setCanceledOnTouchOutside(false);
        dialogChange.show();

        save.setOnClickListener(view1 -> {
            frameDB.deleteAllFrames();
            Intent intent = new Intent(activity, SearchFrames.class);
            activity.startActivity(intent);
            activity.finish();
            dialogChange.dismiss();
        });
    }

    public static void OpenRemove(AppCompatActivity activity, View view) {
        Dialog dialogRemove = new Dialog(activity);
        FrameDB frameDB = new FrameDB(activity);

        Button cancel;
        Button save;

        dialogRemove.setContentView(R.layout.remove_data);
        cancel = dialogRemove.findViewById(R.id.Cancel);
        save = dialogRemove.findViewById(R.id.Save);

        Window window = dialogRemove.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        cancel.setOnClickListener(v -> dialogRemove.dismiss());
        dialogRemove.setCanceledOnTouchOutside(false);
        dialogRemove.show();

        save.setOnClickListener(view1 -> {
            frameDB.deleteAllFrames();
            dialogRemove.dismiss();
            activity.finish();
        });
    }

    public static void updateRecyclerView(AppCompatActivity activity, FrameDB frameDB, FrameListAdapter adapter) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("my_app_settings", Context.MODE_PRIVATE);
        boolean isICMPChecked = sharedPreferences.getBoolean("isICMPChecked", true);
        boolean isTCPChecked = sharedPreferences.getBoolean("isTCPChecked", true);
        boolean isUDPChecked = sharedPreferences.getBoolean("isUDPChecked", true);
        boolean isHTTPChecked = sharedPreferences.getBoolean("isHTTPChecked", true);

        adapter.updateData(frameDB.getAllFramesFiltered(isICMPChecked, isTCPChecked, isUDPChecked, isHTTPChecked));
    }

    public interface OnImportCompleteListener {
        void onImportComplete();
    }

    public static void importFrames(AppCompatActivity activity, FrameDB frameDB, OnImportCompleteListener listener, int start) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    frameDB.deleteAllFrames();
                    String jsonData = JsonReader.readJsonData("https://0c49-46-193-6-178.ngrok-free.app/?start=" + start);

                    try {
                        DataProcessor.processAndSaveData(frameDB, jsonData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    activity.runOnUiThread(() -> {
                        if (listener != null) {
                            listener.onImportComplete();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }
}