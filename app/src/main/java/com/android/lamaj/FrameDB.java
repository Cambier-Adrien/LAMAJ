package com.android.lamaj;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.CheckBox;
import androidx.preference.PreferenceManager;

import org.apache.commons.codec.DecoderException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrameDB {
    private static FrameDB instance;
    private SQLiteDatabase db;

    public FrameDB(Context context) {
        this.db = new FrameDBHelper(context).getWritableDatabase();
    }

    public static FrameDB getInstance(Context context) {
        if (instance == null) {
            instance = new FrameDB(context);
        }

        return instance;
    }

    public void saveFrame(FrameWireshark frame) {
        ContentValues values = new ContentValues();
        values.put("IPSource", frame.getSourceIP());
        values.put("IPDestination", frame.getDestinationIP());
        values.put("Protocol", frame.getProtocol());
        values.put("Payload", frame.getPayload());

        db.insert("Frame", null, values);
    }

    private String getProtocolFromDatabase(FrameWireshark frame) {
        Cursor cursor = db.query("Frame", new String[]{"Protocol"}, "IPSource=? AND IPDestination=?", new String[]{frame.getSourceIP(), frame.getDestinationIP()}, null, null, null);

        if (cursor.moveToFirst()) {
            return frame.getProtocol();
        } else {
            return null;
        }
    }

    public List<FrameWireshark> getAllFrames() {
        Cursor cursor = db.query("Frame", null, null, null, null, null, null);

        List<FrameWireshark> frameList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int ipSourceIndex = cursor.getColumnIndex("IPSource");
                int ipDestinationIndex = cursor.getColumnIndex("IPDestination");
                int protocolIndex = cursor.getColumnIndex("Protocol");
                int payloadIndex = cursor.getColumnIndex("Payload");

                int id = cursor.getInt(idIndex);
                String IPSource = cursor.getString(ipSourceIndex);
                String IPDestination = cursor.getString(ipDestinationIndex);
                String Protocol = cursor.getString(protocolIndex);
                String Payload = cursor.getString(payloadIndex);

                FrameWireshark frame = new FrameWireshark(id, IPSource, IPDestination, Protocol, Payload);
                frameList.add(frame);
            } while (cursor.moveToNext());
        } else {
            return null;
        }

        cursor.close();

        return frameList;
    }

    public void deleteAllFrames() {
        db.delete("Frame", null, null);
    }

    public static void importFrames(FrameDB frameDB) throws IOException, DecoderException {
        int start = 1;
        boolean continueImport = true;

        while (continueImport) {
            String jsonData = JsonReader.readJsonData("http://isis.unice.fr/~gj203594/ext/java/?start=" + start);

            if (jsonData.isEmpty()) {
                break;
            }

            try {
                DataProcessor.processAndSaveData(frameDB, jsonData);
            } catch (IOException | DecoderException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            start++;
        }
    }

    public List<FrameWireshark> getAllFramesFiltered(boolean isICMPChecked, boolean isTCPChecked, boolean isUDPChecked, boolean isHTTPChecked) {
        List<FrameWireshark> frameList = new ArrayList<>();

        Cursor cursor = db.query("Frame", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int ipSourceIndex = cursor.getColumnIndex("IPSource");
                int ipDestinationIndex = cursor.getColumnIndex("IPDestination");
                int protocolIndex = cursor.getColumnIndex("Protocol");
                int payloadIndex = cursor.getColumnIndex("Payload");

                int id = cursor.getInt(idIndex);
                String IPSource = cursor.getString(ipSourceIndex);
                String IPDestination = cursor.getString(ipDestinationIndex);
                String Protocol = cursor.getString(protocolIndex);
                String Payload = cursor.getString(payloadIndex);

                boolean shouldAddFrame = true;

                if (!isICMPChecked && Protocol.equals("ICMP")) {
                    shouldAddFrame = false;
                } else if (!isTCPChecked && Protocol.equals("TCP")) {
                    shouldAddFrame = false;
                } else if (!isUDPChecked && Protocol.equals("UDP")) {
                    shouldAddFrame = false;
                } else if (!isHTTPChecked && Protocol.equals("HTTP")) {
                    shouldAddFrame = false;
                }

                if (shouldAddFrame) {
                    FrameWireshark frame = new FrameWireshark(id, IPSource, IPDestination, Protocol, Payload);
                    frameList.add(frame);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return frameList;
    }
}