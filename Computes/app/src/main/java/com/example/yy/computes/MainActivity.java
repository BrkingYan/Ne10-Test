package com.example.yy.computes;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private List<Integer> N_List;
    private List<String> Algorithm_List;

    private Spinner nSpinner;
    private ArrayAdapter<Integer> nAdapter;
    private Spinner alSpinner;
    private ArrayAdapter<String> alAdapter;

    private Button genIntBtn;
    private Button genFloatBtn;
    private Button resetBtn;
    private Button startBtn;
    private Button neonTestBtnInt;
    private Button neonTestBtnFloat;
    private Button neonTestBtnMul;

    private TextView dataTypeView;
    private TextView fftTimeConsumeView;
    private TextView mixTimeConsumeView;

    private int N;
    private AlgorithmType mAlgorithmType;
    private DataType mDataType;

    /*private List<Integer> intList;
    private List<Float> floatList;*/
    private int[] intData;
    private float[] floatData;

    private Random rand;
    private Thread calThread;

    private double fftTimeConsume;
    private double mixTimeConsume;

    private ExecutorService exec ;

    private static final String TAG = "MainActivity";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                //FFT
                case 1:
                    fftTimeConsume = (double)msg.obj;
                    fftTimeConsumeView.setText(fftTimeConsume + "us");
                    break;
                //Mix
                case 2:
                    mixTimeConsume = (double)msg.obj;
                    mixTimeConsumeView.setText(mixTimeConsume+"us");
            }
        }
    };


    enum AlgorithmType{
        JNI,Neon
    }

    enum DataType{
        Int,Float
    }


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nSpinner = findViewById(R.id.spinner_n);
        alSpinner = findViewById(R.id.spinner_algorithm);
        genIntBtn = findViewById(R.id.generate_int_btn);
        genFloatBtn = findViewById(R.id.generate_float_btn);
        resetBtn = findViewById(R.id.reset_btn);
        startBtn = findViewById(R.id.begin_test_btn);
        neonTestBtnInt = findViewById(R.id.neon_int_test_btn);
        neonTestBtnFloat = findViewById(R.id.neon_float_test_btn);
        neonTestBtnMul = findViewById(R.id.neon_mul_test_btn);

        dataTypeView = findViewById(R.id.data_type_view);
        fftTimeConsumeView = findViewById(R.id.FFT_time_comsume);
        mixTimeConsumeView = findViewById(R.id.Mix_time_comsume);

        //初始化容器
        initContainers();
        //初始化线程池
        exec = Executors.newCachedThreadPool();

        //给下拉菜单添加数据
        loadSpinnerData();

        //给spinner设置adapter
        nAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,N_List);
        nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nSpinner.setAdapter(nAdapter);

        alAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,Algorithm_List);
        alAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alSpinner.setAdapter(alAdapter);

        //设置Spinner的点击事件
        nSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                // 捕捉用户设置的band和duration
                N = N_List.get(position);
                Log.d(TAG,"N:" + N);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (Algorithm_List.get(position)){
                    case "JNI":
                        mAlgorithmType = AlgorithmType.JNI;
                        break;
                    case "Neon":
                        mAlgorithmType = AlgorithmType.Neon;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //给按钮增加监听器
        setClickListeners();
    }


    private void loadSpinnerData(){
        for (int i = 0;i<11;i++){
            N_List.add((int) Math.pow(2,(8+i)));
        }

        Algorithm_List.add("JNI");
        Algorithm_List.add("Neon");
    }

    private void initContainers(){
        N_List = new ArrayList<>();
        Algorithm_List = new ArrayList<>();
        mDataType = DataType.Int;
        mAlgorithmType = AlgorithmType.JNI;

        intData = new int[0];
        floatData = new float[0];
    }

    /*
    * 产生测试数据
    * */
    private void generateIntData(){
        intData = new int[N];
        rand = new Random();
        for (int i = 0;i<N;i++){
            intData[i] = rand.nextInt();
        }
        Toast.makeText(MainActivity.this,"产生了"+N+"个int型数据",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"list len:" + intData.length);
    }

    private void generateFloatData(){
        floatData = new float[N];
        rand = new Random();
        for (int i = 0;i<N;i++){
            floatData[i] = rand.nextFloat();
        }
        Toast.makeText(MainActivity.this,"产生了"+N+"个float型数据",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"list len:" + floatData.length);
    }

    private void setClickListeners(){
        // button的监听器
        genIntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"button is clicked");
                generateIntData();
                mDataType = DataType.Int;
                dataTypeView.setText("int");
            }
        });

        genFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateFloatData();
                mDataType = DataType.Float;
                dataTypeView.setText("float");
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intData = null;
                //floatData = null;
                //N = 0;
                calThread = null;
                fftTimeConsumeView.setText("");
                mixTimeConsumeView.setText("");
                Toast.makeText(MainActivity.this,"请重新设置数据",Toast.LENGTH_SHORT).show();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mDataType){
                    case Int:
                        calThread = new Thread(new CalculateTask(mAlgorithmType,mHandler,intData));
                        break;
                    case Float:
                        calThread = new Thread(new CalculateTask(mAlgorithmType,mHandler,floatData));
                        break;
                }
                calThread.start();
                Toast.makeText(MainActivity.this,"计算开始",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"algorithm:" + mAlgorithmType);

            }
        });

        neonTestBtnInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeonActivity.class);
                intent.putExtra("libName","NE10_test_demo_int");
                startActivity(intent);
                //finish();
            }
        });

        neonTestBtnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeonActivity.class);
                intent.putExtra("libName","NE10_test_demo_float");
                startActivity(intent);
                //finish();
            }
        });

        neonTestBtnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NeonActivity.class);
                intent.putExtra("libName","NE10_test_demo_mul");
                startActivity(intent);
            }
        });
    }
}
