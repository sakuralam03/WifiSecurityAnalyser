package com.example.wifisecurityanalyser.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
