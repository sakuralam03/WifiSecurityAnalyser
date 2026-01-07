package com.example.wifisecurityanalyser.analyzer;

public class RiskAnalyzer {
    public static String analyzeRisk(String security) {
        if (security.contains("WEP")) return "High";
        else if (security.contains("WPA3")) return "Low";
        else if (security.contains("WPA2")) {
            if (security.contains("TKIP")) return "Medium";
            else return "Low";
        } else if (security.contains("WPA")) return "Medium";
        else if (security.equals("[ESS]")) return "High"; // Open Wi-Fi
        else return "Unknown";
    }
}
