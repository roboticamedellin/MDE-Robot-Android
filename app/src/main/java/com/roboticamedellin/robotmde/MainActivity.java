package com.roboticamedellin.robotmde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button ctaLeft;
    Button ctaRight;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_message);
        ctaLeft = (Button) findViewById(R.id.cta_left);
        ctaRight = (Button) findViewById(R.id.cta_right);

        ctaLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftClicked();
            }
        });

        ctaRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClicked();
            }
        });
    }

    void leftClicked() {
        if (flag) {
            textView.setText("Hola");
        } else {
            textView.setText("hallo");
        }
        textView.setText(flag ? "Hola" : "Hallo");
        flag = !flag;
    }

    private void rightClicked() {
        if (flag) {
            textView.setText("UNO");
        } else {
            textView.setText("DOS");
        }
        textView.setText(flag ? "Hola" : "Hallo");
        flag = !flag;
    }

}
