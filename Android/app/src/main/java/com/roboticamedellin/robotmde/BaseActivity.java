package com.roboticamedellin.robotmde;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.roboticamedellin.robotmde.bluetooth.BluetoothController;

import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class BaseActivity extends AppCompatActivity implements BluetoothController.ConnectedListener{

    BluetoothController bluetoothController;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothController = new BluetoothController(this);
        bluetoothController.checkBluetoothState(this);
        bluetoothController.setConnectedListener(this);

        showProgressDialog();

    }

    private void showProgressDialog() {
        dialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true);
    }

    private void hideProgressDialog() {
        if (null != dialog) dialog.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothController.isBluetoothConnected()) {
            hideProgressDialog();
        }
    }

    public void sendMessage(String message) {
            bluetoothController.sendMessage(message);
    }

    @Override
    public void onDeviceConnected() {
        hideProgressDialog();
    }

    @Override
    public void onDeviceDisconnected() {
        showProgressDialog();
    }

    @Override
    public void onDeviceConnectionFailed() {
        finish();
    }

    @Override
    public void loadDeviceList() {
        Intent intent = new Intent(this, DeviceList.class);
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BluetoothState.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothController.connectToBluetoothDevice(data);
                } else {
                    finish();
                }
                break;
            case BluetoothState.REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothController.connectToBluetoothService();
                } else {
                    finish();
                }
                break;
        }
    }
}
