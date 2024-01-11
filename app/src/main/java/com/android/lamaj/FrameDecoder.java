package com.android.lamaj;

import android.util.Base64;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

public class FrameDecoder {

    private static int id = 0;

    private static long sourceIPPosition = 14;
    private static long destinationIPPosition = 24;
    private static long protocolPosition = 23;

    public static FrameWireshark decodeFrame(String hexString) throws DecoderException {
        byte[] bytes = Hex.decodeHex(hexString.toCharArray());
        int newId = id;
        id++;

        String sourceIP = extractSourceIP(bytes);
        String destinationIP = extractDestinationIP(bytes);
        String protocol = extractProtocol(bytes);
        String payload = getPayload(bytes);

        FrameWireshark frame = new FrameWireshark(newId, sourceIP, destinationIP, protocol, payload);

        return frame;
    }

    private static String extractSourceIP(byte[] bytes) throws DecoderException {
        byte[] sourceIPBytes = Arrays.copyOfRange(bytes, (int) sourceIPPosition, (int) (sourceIPPosition + 4));
        String sourceIP = bytesToIPAddress(sourceIPBytes);

        return sourceIP;
    }

    private static String extractDestinationIP(byte[] bytes) throws DecoderException {
        byte[] destinationIPBytes = Arrays.copyOfRange(bytes, (int) destinationIPPosition, (int) (destinationIPPosition + 4));
        String destinationIP = bytesToIPAddress(destinationIPBytes);

        return destinationIP;
    }

    private static String extractProtocol(byte[] bytes) throws DecoderException {
        byte[] protocolBytes = Arrays.copyOfRange(bytes, (int) protocolPosition, (int) (protocolPosition + 1));
        int protocolValue = (int) (protocolBytes[0] & 0xff);
        String protocol;

        switch (protocolValue) {
            default:
                protocol = String.valueOf(protocolValue);
                break;
        }

        return protocol;
    }

    private static String getPayload(byte[] bytes) {
        int payloadPosition = (int) protocolPosition + 1;
        int payloadLength = bytes.length - payloadPosition;
        byte[] payloadBytes = Arrays.copyOfRange(bytes, payloadPosition, bytes.length);
        String payload = Base64.encodeToString(payloadBytes, Base64.DEFAULT);

        return payload;
    }

    private static String bytesToIPAddress(byte[] bytes) {
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            ipAddress.append((int) bytes[i] & 0xff);
            if (i < bytes.length - 1) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }
}