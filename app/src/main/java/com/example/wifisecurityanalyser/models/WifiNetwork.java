package com.example.wifisecurityanalyser.models;

public class WifiNetwork {
    private String ssid;
    private String bssid;
    private int signalStrength;
    private String securityType;
    private String riskLevel;
    private String ssidHash;

    public WifiNetwork(String ssid, String bssid, int signalStrength,
                       String securityType, String riskLevel, String ssidHash) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.signalStrength = signalStrength;
        this.securityType = securityType;
        this.riskLevel = riskLevel;
        this.ssidHash = ssidHash;
    }

    @Override
    public String toString() {
        return "SSID: " + ssid +
                " | BSSID: " + bssid +
                " | Signal: " + signalStrength +
                " | Security: " + securityType +
                " | Risk: " + riskLevel +
                " | Hash: " + ssidHash;
    }
}
