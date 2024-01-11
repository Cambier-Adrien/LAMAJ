package com.android.lamaj;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

public class FrameDecoder {

    private static int id = 0;
    private static String hexFrame;

    public static FrameWireshark decodeFrame(String hexString) throws DecoderException {
        hexFrame = Hex.encodeHexString(Hex.decodeHex(hexString.toCharArray()));
        int newId = id++;

        String sourceIP = extractSourceIP();
        String destinationIP = extractDestinationIP();
        String protocol = extractProtocol();
        String payload = getPayload();

        return new FrameWireshark(newId, sourceIP, destinationIP, protocol, payload);
    }

    private static String extractSourceIP() throws DecoderException {
        String etherType = hexFrame.substring(28, 28 + 4);
        String srcIP = null;

        // IPv4
        if ("0800".equals(etherType)) {
            srcIP = bytesToIpAddress(hexFrame.substring(56, 56 + 8));
        }

        // IPv6
        else if ("86dd".equals(etherType)) {
            srcIP = hexToIpv6(hexFrame.substring(48, 48 + 32));
        }

        // ARP
        else if ("0806".equals(etherType)) {
            srcIP = bytesToIpAddress(hexFrame.substring(60, 60 + 8));
        }

        return srcIP;
    }

    private static String extractDestinationIP() throws DecoderException {
        String etherType = hexFrame.substring(28, 28 + 4);
        String dstIp = null;

        // IPv4
        if ("0800".equals(etherType)) {
            dstIp = bytesToIpAddress(hexFrame.substring(64, 64 + 8));
        }

        // IPv6
        else if ("86dd".equals(etherType)) {
            dstIp = hexToIpv6(hexFrame.substring(80, 80 + 32));
        }

        // ARP
        else if ("0806".equals(etherType)) {
            dstIp = bytesToIpAddress(hexFrame.substring(80, 80 + 8));
        }

        return dstIp;
    }

    private static String extractProtocol() throws DecoderException {
        String etherTypeHex = hexFrame.substring(28, 32);
        int etherTypeValue = Integer.parseInt(etherTypeHex, 16);

        switch (etherTypeValue) {
            case 0x0800:
                return "IPv4";
            case 0x86DD:
                return "IPv6";
            case 0x0806:
                return "ARP";
            default:
                return "Other : " + etherTypeValue;
        }
    }

    private static String getPayload() {
        return hexFrame;
    }


    private static String bytesToIpAddress(String hex) {
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            int octet = Integer.parseInt(hex.substring(i, i + 2), 16);
            ipAddress.append(octet);
            if (i < hex.length() - 2) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }

    private static String hexToIpv6(String hex) {
        StringBuilder ipv6 = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 4) {
            ipv6.append(hex.substring(i, i + 4));
            if (i < hex.length() - 4) {
                ipv6.append(":");
            }
        }
        return ipv6.toString();
    }
}
