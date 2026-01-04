package com.example.wifisecurityanalyser;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wifisecurityanalyser.analyzer.RiskAnalyzer;
import com.example.wifisecurityanalyser.crypto.CryptoUtils;
import com.example.wifisecurityanalyser.models.WifiNetwork;
import com.example.wifisecurityanalyser.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 100;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        if (PermissionUtils.hasLocationPermission(this)) {
            scanWifiNetworks();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION);
        }
    }

    private void scanWifiNetworks() {
        List<ScanResult> results = wifiManager.getScanResults();
        List<WifiNetwork> networks = new ArrayList<>();

        for (ScanResult result : results) {
            String risk = RiskAnalyzer.analyzeRisk(result.capabilities);
            String hash = CryptoUtils.hashSSID(result.SSID);

            WifiNetwork network = new WifiNetwork(
                    result.SSID,
                    result.BSSID,
                    result.level,
                    result.capabilities,
                    risk,
                    hash
            );
            networks.add(network);

            Log.d("WiFi", network.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            scanWifiNetworks();
        }
    }
}
