package com.android.lamaj;

import org.apache.commons.codec.DecoderException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class DataProcessor {

    public static void processAndSaveData(FrameDB frameDB, String jsonData) throws IOException, DecoderException, JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            String hexString = jsonArray.getString(i);

            FrameWireshark frame = FrameDecoder.decodeFrame(hexString);

            frameDB.saveFrame(frame);
        }
    }
}
