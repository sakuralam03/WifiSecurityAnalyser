package com.example.wifisecurityanalyser;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisecurityanalyser.analyzer.RiskAnalyzer;
import com.example.wifisecurityanalyser.crypto.CryptoUtils;
import com.example.wifisecurityanalyser.models.WifiNetwork;
import com.example.wifisecurityanalyser.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 100;
    private WifiManager wifiManager;
    private WifiAdapter adapter;
    private List<WifiNetwork> networks = new ArrayList<>();
    private RecyclerView recyclerView;

    // 📡 BroadcastReceiver to listen for scan completion
    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanWifiNetworks(); // ✅ Fresh results available
            } else {
                Log.e("WiFi", "Scan failed or returned cached results");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        recyclerView = findViewById(R.id.wifiRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WifiAdapter(networks, network -> {
            Intent intent = new Intent(MainActivity.this, NetworkTestActivity.class);
            intent.putExtra("network", network);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // ✅ Request Android 13+ permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.NEARBY_WIFI_DEVICES},
                    REQUEST_CODE_LOCATION);
        }

        // ✅ Request location permission
        if (PermissionUtils.hasLocationPermission(this)) {
            registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION);
        }
    }

    // 🔍 Process scan results and update RecyclerView
    private void scanWifiNetworks() {
        List<ScanResult> results = wifiManager.getScanResults();
        networks.clear();

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
        }

        adapter.notifyDataSetChanged(); // ✅ Refresh existing adapter
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanReceiver); // 🧹 Prevent leaks
    }
}
