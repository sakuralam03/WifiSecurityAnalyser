package com.example.wifisecurityanalyser.models;

import android.os.Parcel;
import android.os.Parcelable;

public class WifiNetwork implements Parcelable {
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

    protected WifiNetwork(Parcel in) {
        ssid = in.readString();
        bssid = in.readString();
        signalStrength = in.readInt();
        securityType = in.readString();
        riskLevel = in.readString();
        ssidHash = in.readString();
    }

    public static final Creator<WifiNetwork> CREATOR = new Creator<WifiNetwork>() {
        @Override
        public WifiNetwork createFromParcel(Parcel in) {
            return new WifiNetwork(in);
        }

        @Override
        public WifiNetwork[] newArray(int size) {
            return new WifiNetwork[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ssid);
        dest.writeString(bssid);
        dest.writeInt(signalStrength);
        dest.writeString(securityType);
        dest.writeString(riskLevel);
        dest.writeString(ssidHash);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getSsid() { return ssid; }
    public String getBssid() { return bssid; }
    public int getSignalStrength() { return signalStrength; }
    public String getSecurityType() { return securityType; }
    public String getRiskLevel() { return riskLevel; }
    public String getSsidHash() { return ssidHash; }
}
