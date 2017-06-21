package com.roboticamedellin.robotmde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button ctaLeft;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctaLeft = (Button) findViewById(R.id.cta_left);
        textView = (TextView) findViewById(R.id.text_message);

        ctaLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void setMessage() {
        if (flag) {
            textView.setText("Hola");
        } else {
            textView.setText("hallo");
        }
//        textView.setText(flag ? "Hola" : "Hallo");
        flag = !flag;
    }

}
