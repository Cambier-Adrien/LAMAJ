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
            case 0x0806:
                return "ARP";
            case 0x0801:
                return "MPLS Unicast";
            case 0x0802:
                return "MPLS Multicast";
            case 0x0835:
                return "RARP";
            case 0x0842:
                return "Wake-on-LAN";
            case 0x22F3:
                return "IETF TRILL";
            case 0x6003:
                return "DECnet Phase IV";
            case 0x8035:
                return "Reverse ARP";
            case 0x809B:
                return "AppleTalk";
            case 0x86DD:
                return "IPv6";
            case 0x8808:
                return "Ethernet flow control";
            case 0x8847:
                return "MPLS unicast";
            case 0x8848:
                return "MPLS multicast";
            case 0x9000:
                return "Ethernet II";
            case 0x0100:
                return "ICMP";
            case 0x0600:
                return "TCP";
            case 0x1100:
                return "UDP";
            case 0x5000:
                return "ESP";
            case 0x8400:
                return "ICMPv6";
            case 0x8900:
                return "OSPF";
            case 0x8f00:
                return "L2TP";
            case 0x8000:
                return "HTTP";
            default:
                return "Other : " + etherTypeValue;
        }
    }

    private static String getPayload() {
        int payloadPosition = 40;
        int payloadLength = hexFrame.length() - payloadPosition;
        String payloadHex = hexFrame.substring(payloadPosition);

        StringBuilder payloadText = new StringBuilder();
        for (int i = 0; i < payloadHex.length(); i += 2) {
            String hexByte = payloadHex.substring(i, i + 2);
            int decimalValue = Integer.parseInt(hexByte, 16);
            char asciiChar = (char) decimalValue;

            payloadText.append(asciiChar);
        }

        return payloadText.toString();
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
