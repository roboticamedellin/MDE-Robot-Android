package com.roboticamedellin.robotmde.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Based on https://github.com/anoochit/android-robot-bluetooth-joystick
 * also Based on https://github.com/akexorcist/Android-BluetoothSPPLibrary
 */

public class BluetoothController implements BluetoothSPP.BluetoothConnectionListener, BluetoothSPP.OnDataReceivedListener {

    private Context context;
    private BluetoothSPP bluetoothSPP;
    private Boolean bluetoothConnected = false;

    private ConnectedListener connectedListener;
    private DataListener dataListener;

    public BluetoothController(final Context context) {
        this.context = context;
        bluetoothSPP = new BluetoothSPP(context);

        bluetoothSPP.setBluetoothConnectionListener(this);
        bluetoothSPP.setOnDataReceivedListener(this);
    }

    public void checkBluetoothState(Activity activity) {
        if (bluetoothSPP.isBluetoothEnabled()) {
            if (!this.bluetoothConnected) {
                bluetoothSPP.setupService();
                bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                connectedListener.loadDeviceList();
            }
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, BluetoothState.REQUEST_ENABLE_BT);
        }
    }

    public Boolean isBluetoothConnected() {
        return bluetoothConnected;
    }

    public void showMessageIssue() {
        Toast.makeText(context, "Problems", Toast.LENGTH_SHORT).show();
    }

    public BluetoothSPP getBluetoothSPP() {
        return bluetoothSPP;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
        switch (requestCode) {
            case BluetoothState.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothSPP.connect(data);
                } else {
                    showMessageIssue();
                    activity.finish();
                }
                break;
            case BluetoothState.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothSPP.setupService();
                    bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                    connectedListener.loadDeviceList();
                } else {
                    activity.finish();
                }
                break;
        }
    }

    public void setConnectedListener(ConnectedListener connectedListener) {
        this.connectedListener = connectedListener;
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public void connectToBluetoothDevice(Intent data) {
        bluetoothSPP.connect(data);
    }

    public void connectToBluetoothService(){
        bluetoothSPP.setupService();
        bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
        connectedListener.loadDeviceList();
    }

    @Override
    public void onDeviceConnected(String name, String address) {
        // Do something when successfully connected
        Toast.makeText(context, "Connected...", Toast.LENGTH_SHORT).show();
        bluetoothConnected = true;
        if(null != connectedListener) connectedListener.onDeviceConnected();
    }

    @Override
    public void onDeviceDisconnected() {
        // Do something when connection was disconnected
        Toast.makeText(context, "Disconnected...", Toast.LENGTH_SHORT).show();
        bluetoothConnected = false;
        if(null != connectedListener) connectedListener.onDeviceDisconnected();
    }

    @Override
    public void onDeviceConnectionFailed() {
        // Do something when connection failed
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        bluetoothConnected = false;
        if(null != connectedListener) connectedListener.onDeviceConnectionFailed();
    }

    @Override
    public void onDataReceived(byte[] data, String message) {
        Log.i(this.getClass().getName(), "onDataReceived: " + message);
        dataListener.onDataReceived(message);
    }

    public interface ConnectedListener {
        void onDeviceConnected();
        void onDeviceDisconnected();
        void onDeviceConnectionFailed();
        void loadDeviceList();
    }

    public interface DataListener{
        void onDataReceived(String message);
    }
}