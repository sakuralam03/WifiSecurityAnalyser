package com.example.wifisecurityanalyser;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifisecurityanalyser.models.WifiNetwork;

import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiViewHolder> {

    private List<WifiNetwork> wifiList;
    private OnTestClickListener listener;

    public interface OnTestClickListener {
        void onTestClick(WifiNetwork network);
    }

    public WifiAdapter(List<WifiNetwork> wifiList, OnTestClickListener listener) {
        this.wifiList = wifiList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wifi, parent, false);
        return new WifiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WifiViewHolder holder, int position) {
        WifiNetwork network = wifiList.get(position);

        holder.ssidText.setText(network.getSsid());
        holder.signalText.setText("Signal: " + network.getSignalStrength() + " dBm");
        holder.securityText.setText("Security: " + network.getSecurityType());
        holder.riskText.setText("Risk: " + network.getRiskLevel());

        switch (network.getRiskLevel()) {
            case "High":
                holder.riskText.setTextColor(Color.RED);
                break;
            case "Medium":
                holder.riskText.setTextColor(Color.parseColor("#FFA500"));
                break;
            case "Low":
                holder.riskText.setTextColor(Color.GREEN);
                break;
            default:
                holder.riskText.setTextColor(Color.GRAY);
        }

        holder.testButton.setOnClickListener(v -> listener.onTestClick(network));
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    static class WifiViewHolder extends RecyclerView.ViewHolder {
        TextView ssidText, signalText, securityText, riskText;
        Button testButton;

        WifiViewHolder(View itemView) {
            super(itemView);
            ssidText = itemView.findViewById(R.id.ssidText);
            signalText = itemView.findViewById(R.id.signalText);
            securityText = itemView.findViewById(R.id.securityText);
            riskText = itemView.findViewById(R.id.riskText);
            testButton = itemView.findViewById(R.id.testButton);
        }
    }
}
