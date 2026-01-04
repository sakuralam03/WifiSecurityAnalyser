package com.example.wifisecurityanalyser.analyzer;

public class RiskAnalyzer {

    public static String analyzeRisk(String security) {
        if (security.contains("WEP")) {
            return "High"; // ❌ Very insecure
        } else if (security.contains("WPA3")) {
            return "Low";  // ✅ Most secure
        } else if (security.contains("WPA2")) {
            if (security.contains("TKIP")) {
                return "Medium"; // ⚠️ WPA2 with TKIP is weak
            } else {
                return "Low"; // ✅ WPA2 with AES/CCMP is strong
            }
        } else if (security.contains("WPA")) {
            return "Medium"; // ⚠️ WPA (older standard, weaker than WPA2)
        } else if (security.equals("[ESS]")) {
            return "High"; // ⚠️ Open Wi-Fi, no encryption
        } else {
            return "Unknown";
        }
    }
}
