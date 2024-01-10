package com.android.lamaj;

import android.util.Base64;
import android.view.View;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class FrameWireshark {

    private int id;
    private String sourceIP;
    private String destinationIP;
    private String protocol;
    private String payload;
    private boolean enabled;

    public FrameWireshark(int id, String sourceIP, String destinationIP, String protocol, String payload) {
        this.id = id;
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.protocol = protocol;
        this.payload = payload;
    }

    public int getID() {
        return id;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPayload() {
        return payload;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public void setDestinationIP(String destinationIP) {
        this.destinationIP = destinationIP;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public static String decodeHexString(String hexString) {
        byte[] bytes = new byte[0];
        try {
            bytes = Hex.decodeHex(hexString.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public String toString() {
        return "FrameWireshark{" +
                "id=" + id +
                ", sourceIP='" + sourceIP + '\'' +
                ", destinationIP='" + destinationIP + '\'' +
                ", protocol='" + protocol + '\'' +
                ", payload='" + payload +
                '}';
    }
}