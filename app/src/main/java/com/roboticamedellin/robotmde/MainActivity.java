package com.roboticamedellin.robotmde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_message)
    TextView textView;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cta_left)
    public void leftClicked() {
        if (flag) {
            textView.setText("Hola");
        } else {
            textView.setText("hallo");
        }
        textView.setText(flag ? "Hola" : "Hallo");
        flag = !flag;
    }

    @OnClick(R.id.cta_right)
    public void rightClicked() {
        if (flag) {
            textView.setText("UNO");
        } else {
            textView.setText("DOS");
        }
        textView.setText(flag ? "Hola" : "Hallo");
        flag = !flag;
    }

}
