package com.roboticamedellin.robotmde;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.text_message)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cta_left)
    public void leftClicked() {
        sendMessage("s-1");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage("c-1");
            }
        }, 250);
    }

    @OnClick(R.id.cta_right)
    public void rightClicked() {
        sendMessage("s-1");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage("u-1");
            }
        }, 250);
    }

}
