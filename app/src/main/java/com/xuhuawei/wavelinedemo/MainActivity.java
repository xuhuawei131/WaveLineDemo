package com.xuhuawei.wavelinedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WavePathView.OnWavePathListener {

    private WavePathView wavePathView;
    private TextView btn_undo;
    private TextView btn_redo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wavePathView=findViewById(R.id.wavePathView);

        View btn_0=findViewById(R.id.btn_0);
        View btn_1=findViewById(R.id.btn_1);
        View btn_2=findViewById(R.id.btn_2);

        btn_undo=findViewById(R.id.btn_undo);
        btn_redo=findViewById(R.id.btn_redo);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_undo.setOnClickListener(this);
        btn_redo.setOnClickListener(this);

        wavePathView.setOnWavePathListener(this);
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
            case R.id.btn_undo:
                wavePathView.undoDoolingPaint();
                break;
            case R.id.btn_redo:
                wavePathView.redoDoolingPaint();
                break;
        }
    }

    @Override
    public void onUndoEvent(boolean isCanUndo) {
        btn_undo.setEnabled(isCanUndo);
    }

    @Override
    public void onRedoEvent(boolean isCanRedo) {
        btn_redo.setEnabled(isCanRedo);
    }
}
