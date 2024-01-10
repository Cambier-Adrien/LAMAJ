package com.android.lamaj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
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
            /* try {
               FrameDB.importFrames(frameDB);
            } catch (IOException | DecoderException e) {
                e.printStackTrace();
            } */
            frameDB.saveFrame(new FrameWireshark(1,"192.168.1.1","100.10.9.9","TCP","Hello"));
            frameDB.saveFrame(new FrameWireshark(2,"192.168.1.1","100.10.9.4","TCP","Hello"));
            frameDB.saveFrame(new FrameWireshark(3,"192.168.1.2","100.10.9.4","UDP","Hello"));
            frameDB.saveFrame(new FrameWireshark(4,"192.168.1.2","100.10.9.9","ICMP","Hello"));
            frameDB.saveFrame(new FrameWireshark(5,"192.168.1.1","100.10.9.9","TCP","Hello"));
            frameDB.saveFrame(new FrameWireshark(6,"192.168.1.1","100.10.9.4","TCP","Hello"));
            frameDB.saveFrame(new FrameWireshark(7,"192.168.1.2","100.10.9.4","UDP","Hello"));
            frameDB.saveFrame(new FrameWireshark(8,"192.168.1.2","100.10.9.9","ICMP","Hello"));

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
            try {
                FrameDecoder frameDecoder = new FrameDecoder();
                String hexFrame = "0001000100069020c2dfc0000000080600010800060400019020c2dfc000863b8bfe000000000000863b8bcd000000000000000000000000000000000000";
                FrameWireshark frame = frameDecoder.decodeFrame(hexFrame);
                String sourceIP = frame.getSourceIP();
                String destinationIP = frame.getDestinationIP();
                String protocol = frame.getProtocol();
                String payload = frame.getPayload();
                frameDB.saveFrame(new FrameWireshark(1, sourceIP, destinationIP, protocol, payload));
               FrameDB.importFrames(frameDB);
            } catch (IOException | DecoderException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(activity, SearchFrames.class);
            activity.startActivity(intent);
            dialogChange.dismiss();
            activity.finish();
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

    public static void updateRecyclerView(AppCompatActivity activity, FrameDB frameDB, RecyclerView recyclerView, FrameListAdapter adapter) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("my_app_settings", Context.MODE_PRIVATE);
        boolean isICMPChecked = sharedPreferences.getBoolean("isICMPChecked", true);
        boolean isTCPChecked = sharedPreferences.getBoolean("isTCPChecked", true);
        boolean isUDPChecked = sharedPreferences.getBoolean("isUDPChecked", true);
        boolean isHTTPChecked = sharedPreferences.getBoolean("isHTTPChecked", true);

        adapter.updateData(frameDB.getAllFramesFiltered(isICMPChecked, isTCPChecked, isUDPChecked, isHTTPChecked));
    }
}