package com.roboticamedellin.robotmde;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

        showDialog();

    }

    void showDialog() {
//        dialog = ProgressDialog.show(this, "", getString("Por favor espere..."), true);
    }

    public void hideWaitingOverlay() {
        if (null != dialog) dialog.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothController.getBtConnect()) {
            hideWaitingOverlay();
        }
    }

    public void sendMessage(String message) {
        // TODO: Validate connection
        bluetoothController.getBt().send(message + "\n", false);
    }

    @Override
    public void onDeviceConnected() {

    }

    @Override
    public void onDeviceDisconnected() {

    }

    @Override
    public void onDeviceConnectionFailed() {

    }
}
