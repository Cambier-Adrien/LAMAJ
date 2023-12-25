package com.android.lamaj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public abstract class DialogUse extends Context {
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

        Button cancel;
        Button save;
        EditText editText;

        dialogImport.setContentView(R.layout.import_data);
        cancel = dialogImport.findViewById(R.id.Cancel);
        save = dialogImport.findViewById(R.id.Save);
        editText = dialogImport.findViewById(R.id.Import);

        Window window = dialogImport.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        editText.requestFocus();
        cancel.setOnClickListener(v -> dialogImport.dismiss());

        dialogImport.show();

        save.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, SearchFrames.class);
            activity.startActivity(intent);
            dialogImport.dismiss();
        });
    }

    public static void OpenRemove(AppCompatActivity activity, View view) {
        Dialog dialogRemove = new Dialog(activity);

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

        dialogRemove.show();

        save.setOnClickListener(view1 -> {
            activity.finish();
            dialogRemove.dismiss();
        });
    }
}
