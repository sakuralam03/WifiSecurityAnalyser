package com.example.wifisecurityanalyser;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wifisecurityanalyser.models.WifiNetwork;

public class NetworkTestActivity extends AppCompatActivity {

    private TextView ssidText, bssidText, signalText, securityText, riskText, hashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        ssidText = findViewById(R.id.ssidText);
        bssidText = findViewById(R.id.bssidText);
        signalText = findViewById(R.id.signalText);
        securityText = findViewById(R.id.securityText);
        riskText = findViewById(R.id.riskText);
        hashText = findViewById(R.id.hashText);

        WifiNetwork network = getIntent().getParcelableExtra("network");

        if (network != null) {
            ssidText.setText("SSID: " + network.getSsid());
            bssidText.setText("BSSID: " + network.getBssid());
            signalText.setText("Signal: " + network.getSignalStrength() + " dBm");
            securityText.setText("Security: " + network.getSecurityType());
            riskText.setText("Risk Level: " + network.getRiskLevel());
            hashText.setText("SSID Hash: " + network.getSsidHash());

            switch (network.getRiskLevel()) {
                case "High":
                    riskText.setTextColor(Color.RED);
                    break;
                case "Medium":
                    riskText.setTextColor(Color.parseColor("#FFA500"));
                    break;
                case "Low":
                    riskText.setTextColor(Color.GREEN);
                    break;
                default:
                    riskText.setTextColor(Color.GRAY);
            }
        } else {
            ssidText.setText("No network data received");
        }
    }
}
