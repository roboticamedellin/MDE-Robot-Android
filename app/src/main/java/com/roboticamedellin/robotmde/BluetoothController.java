package com.roboticamedellin.robotmde;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class BluetoothController implements BluetoothSPP.BluetoothConnectionListener, BluetoothSPP.OnDataReceivedListener {

    private Context context;
    private BluetoothSPP bluetoothSPP;
    private Boolean btConnect = false;

    private ConnectedListener connectedListener;
    private DataListener dataListener;

    public BluetoothController(final Context context) {
        this.context = context;
        bluetoothSPP = new BluetoothSPP(context);

        bluetoothSPP.setBluetoothConnectionListener(this);
        bluetoothSPP.setOnDataReceivedListener(this);
    }

    public Boolean getBtConnect() {
        return btConnect;
    }

    public BluetoothSPP getBluetoothSPP() {
        return bluetoothSPP;
    }

    public void setConnectedListener(ConnectedListener connectedListener) {
        this.connectedListener = connectedListener;
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void onDeviceConnected(String name, String address) {
        // Do something when successfully connected
        Toast.makeText(context, "Connected...", Toast.LENGTH_SHORT).show();
        btConnect = true;
        if (null != connectedListener) connectedListener.onDeviceConnected();
    }

    @Override
    public void onDeviceDisconnected() {
        // Do something when connection was disconnected
        Toast.makeText(context, "Disconnected...", Toast.LENGTH_SHORT).show();
        btConnect = false;
        // TODO: Create pull request on resource 1
        // btConnect = true;
    }

    @Override
    public void onDeviceConnectionFailed() {
        // Do something when connection failed
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        btConnect = false;
    }

    @Override
    public void onDataReceived(byte[] data, String message) {
        // TODO: Add RX TO DO CALLS
        Log.i(this.getClass().getName(), "onDataReceived: " + message);
        dataListener.onDataReceived(message);
    }

    public interface ConnectedListener {
        void onDeviceConnected();

        void onDeviceDisconnected();

        void onDeviceConnectionFailed();
    }

    public interface DataListener {
        void onDataReceived(String message);
    }
}