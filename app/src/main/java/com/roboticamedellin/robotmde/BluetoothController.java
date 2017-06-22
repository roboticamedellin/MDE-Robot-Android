package com.roboticamedellin.robotmde;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

/**
 * Based on https://github.com/anoochit/android-robot-bluetooth-joystick
 * also Based on https://github.com/akexorcist/Android-BluetoothSPPLibrary
 */

public class BluetoothController implements BluetoothSPP.BluetoothConnectionListener, BluetoothSPP.OnDataReceivedListener {

    private Context context;
    private BluetoothSPP bt;
    private Boolean btConnect = false;

    private ConnectedListener connectedListener;
    private DataListener dataListener;

    public BluetoothController(final Context context) {
        this.context = context;
        bt = new BluetoothSPP(context);

        bt.setBluetoothConnectionListener(this);
        bt.setOnDataReceivedListener(this);
    }

    public void checkBluetoothState(Activity activity) {
        if (bt.isBluetoothEnabled()) {
            if (!this.btConnect) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                loadDeviceList(activity);
            }
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, BluetoothState.REQUEST_ENABLE_BT);
        }
    }

    public Boolean getBtConnect() {
        return btConnect;
    }

    private void loadDeviceList(Activity activity) {
        Intent intent = new Intent(context, DeviceList.class);
        activity.startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
    }

    public void showMessageIssue() {
        Toast.makeText(context, "Problems", Toast.LENGTH_SHORT).show();
    }

    public BluetoothSPP getBt() {
        return bt;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
        switch (requestCode) {
            case BluetoothState.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    bt.connect(data);
                } else {
                    showMessageIssue();
                    activity.finish();
                }
                break;
            case BluetoothState.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    bt.setupService();
                    bt.startService(BluetoothState.DEVICE_OTHER);
                    loadDeviceList(activity);
                } else {
                    // Do something if user doesn't choose any device (Pressed back)
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

    @Override
    public void onDeviceConnected(String name, String address) {
        // Do something when successfully connected
        Toast.makeText(context, "Connected...", Toast.LENGTH_SHORT).show();
        btConnect = true;
        if(null != connectedListener) connectedListener.onDeviceConnected();
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
        Log.i(this.getClass().getName(), "onDataReceived: " + message);
        dataListener.onDataReceived(message);
    }

    public interface ConnectedListener {
        void onDeviceConnected();
        void onDeviceDisconnected();
        void onDeviceConnectionFailed();
    }

    public interface DataListener{
        void onDataReceived(String message);
    }
}