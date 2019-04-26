package com.xuhuawei.wavelinedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WavePathView wavePathView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wavePathView=findViewById(R.id.wavePathView);

        View btn_0=findViewById(R.id.btn_0);
        View btn_1=findViewById(R.id.btn_1);
        View btn_2=findViewById(R.id.btn_2);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_0:
                wavePathView.setPathStyle(0);
                break;
            case R.id.btn_1:
                wavePathView.setPathStyle(1);
                break;
            case R.id.btn_2:
                wavePathView.setPathStyle(2);
                break;
        }
    }
}
