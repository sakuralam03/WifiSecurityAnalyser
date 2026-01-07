package com.example.wifisecurityanalyser.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.Manifest;   // <-- Add this line

import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasWifiPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.NEARBY_WIFI_DEVICES
        ) == PackageManager.PERMISSION_GRANTED;
    }
}
