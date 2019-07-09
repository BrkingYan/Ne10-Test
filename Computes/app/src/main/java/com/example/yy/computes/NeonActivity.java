package com.example.yy.computes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class NeonActivity extends AppCompatActivity {
    private TextView resultView;
    private TextView infoView;
    private Button backBtn;

    private ExecutorService exec;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 3:String result = (String) msg.obj;
                        resultView.setText(result);
                        infoView.setText("<result>");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neon);
        resultView = findViewById(R.id.neon_test_view);
        infoView = findViewById(R.id.info_view);
        backBtn = findViewById(R.id.back_btn);

        infoView.setText("calculating ......");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //infoView.setText("");
                finish();
            }
        });

        exec = Executors.newCachedThreadPool();

        Intent intent = getIntent();
        String libName = intent.getStringExtra("libName");

        NeonUtil loader = new NeonUtil(libName,mHandler);
        new Thread(loader).start();
    }

}
