package com.android.lamaj;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FrameDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Frame.db";
    private static final int DATABASE_VERSION = 1;

    public FrameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Frame (id INTEGER PRIMARY KEY, IPSource TEXT, IPDestination TEXT, Protocol TEXT, Payload TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Frame");
        onCreate(db);
    }
}