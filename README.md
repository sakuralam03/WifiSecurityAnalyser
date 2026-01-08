# 📡 Wi-Fi Security Analyzer

Wi-Fi Security Analyzer is an Android application built with **Android Studio** that scans nearby Wi-Fi networks, analyzes their security protocols, and reports risk levels (Low, Medium, High).  
It is designed for **educational and awareness purposes** — helping users understand the relative strength of Wi-Fi security standards.

---

## 🚀 Features
- Scan nearby Wi-Fi networks using Android Wi-Fi APIs.
- Display SSID, BSSID, signal strength, and security type.
- Analyze security protocols (WEP, WPA/WPA2, WPA3).
- Assign risk levels:
  - **High** → WEP or Open networks
  - **Medium** → WPA/WPA2 with weaker ciphers (e.g., TKIP)
  - **Low** → WPA3 or WPA2 with AES
- Crypto utilities for hashing and fingerprinting (via JCA/BouncyCastle).
- Clean UI with RecyclerView for listing networks.
- Permission handling for modern Android versions.

---

## 📂 Project Structure
WifiSecurityAnalyser/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/wifisecurityanalyser/
│   │   │   │    ├── MainActivity.java                            # Entry point
│   │   │   │    ├── WifiAdapter.java                              # RecyclerView adapter
│   │   │   │    ├── analyzer/                      # Risk analysis logic
│   │   │   │    │    └── RiskAnalyzer.java
│   │   │   │    ├── crypto/                        # Crypto utilities
│   │   │   │    │    └── CryptoUtils.java
│   │   │   │    ├── models/                        # Data models
│   │   │   │    │    └── WifiNetwork.java
│   │   │   │    └── utils/                         # Helpers
│   │   │   │         └── PermissionUtils.java
│   │   │   ├── res/
│   │   │   │    ├── layout/
│   │   │   │    │    ├── activity_main.xml         # Main screen UI
│   │   │   │    │    └── item_wifi.xml             # RecyclerView item UI
│   │   │   │    ├── values/
│   │   │   │    │    └── strings.xml                              # Text resources
│   │   │   │    └── mipmap/                        # App icons
│   │   │   ├── AndroidManifest.xml                                  # Permissions + app config
│   │   └── test/                                   # Unit tests
├── gradle/
└── build.gradle



---

## 📋 Requirements
- **Android Studio** (latest stable version)
- **Minimum SDK**: 23 (Android 6.0)
- **Target SDK**: 33+
- Physical device (emulators don’t expose Wi-Fi scanning)
- Runtime permissions for Location (required since Android 10)

---

## ⚙️ Setup & Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WifiSecurityAnalyser.git
Last Saturday
You said
Wi-Fi Security Analyzer	Medium	High	Android Wi-Fi APIs, crypto libs     how do i make this app on androis studio
Copilot said
Got it — you want to build a Wi-Fi Security Analyzer app in Android Studio that uses Android Wi-Fi APIs and crypto libraries. Let’s break this down step by step so you can scaffold it properly:

🛠️ Step 1: Define the Scope
Your app should:

Scan Wi-Fi networks (SSID, BSSID, signal strength, security type).

Analyze security protocols (WEP, WPA, WPA2, WPA3).

Perform cryptographic checks (e.g., hash validation, weak key detection).

Report risk levels (Medium, High, etc.).

📚 Step 2: Set Up Android Studio Project
Open Android Studio → New Project → Empty Activity.

Configure:

Minimum SDK: Android 8.0 (API 26) or higher (Wi-Fi APIs are stable here).

Add permissions in AndroidManifest.xml:

xml


Copy
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.INTERNET"/>
⚠️ Since Android 10+, Wi-Fi scanning requires location permission.

📡 Step 3: Use Android Wi-Fi APIs
Access Wi-Fi networks via WifiManager:

java


Copy
WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
List<ScanResult> results = wifiManager.getScanResults();
for (ScanResult result : results) {
    Log.d("WiFi", "SSID: " + result.SSID + " | Security: " + result.capabilities);
}
result.capabilities tells you if it’s WEP, WPA/WPA2-PSK, WPA3, etc.

🔐 Step 4: Integrate Crypto Libraries
Use Java Cryptography Architecture (JCA) or BouncyCastle for deeper checks.

Example: Hashing SSID for fingerprinting.

java


Copy
MessageDigest digest = MessageDigest.getInstance("SHA-256");
byte[] hash = digest.digest(result.SSID.getBytes(StandardCharsets.UTF_8));
You can also test weak password dictionaries (educational use only).

📊 Step 5: Risk Analysis Logic
Create a scoring system:

WEP → High risk

WPA/WPA2-PSK with weak cipher (TKIP) → Medium risk

WPA3 → Low risk

Example:

java


Copy
String security = result.capabilities;
String risk;
if (security.contains("WEP")) risk = "High";
else if (security.contains("WPA2") && security.contains("TKIP")) risk = "Medium";
else risk = "Low";
🎨 Step 6: UI Design
Use RecyclerView to list networks.

Show:

SSID

Signal strength

Security type

Risk level (color-coded: red, orange, green).

