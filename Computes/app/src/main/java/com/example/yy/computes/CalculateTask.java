package com.example.yy.computes;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;


public class CalculateTask implements Runnable {

    private MainActivity.AlgorithmType mAlgorithmType;

    private int[] intData;
    private float[] floatData;
    private int N;

    private Handler mHandler;

    private static double fftTime;
    private static double mixTime;
    //private static int counter = 0;

    private static final int TYPE_FFT = 1;
    private static final int TYPE_MIX = 2;

    CalculateTask(MainActivity.AlgorithmType algorithmType,Handler handler,int[] data){
        this.mAlgorithmType = algorithmType;
        intData = Arrays.copyOf(data,data.length);
        N = data.length;
        floatData = null;
        mHandler = handler;
    }

    CalculateTask(MainActivity.AlgorithmType algorithmType,Handler handler,float[] data){
        this.mAlgorithmType = algorithmType;
        floatData = Arrays.copyOf(data,data.length);
        N = data.length;
        intData = null;
        mHandler = handler;
    }

    @Override
    public void run() {
        Log.d("task","into run");
        int count = 0;

        switch (mAlgorithmType){
            case JNI:
                if (intData!=null){
                    testFFT_Int();
                    testMix_Int();
                }else if (floatData != null){
                    testFFT_Float();
                    testMix_Float();
                }
                break;
            case Neon:
                break;
        }

        Message fftMsg = Message.obtain(mHandler);
        fftMsg.what = TYPE_FFT;// 1
        fftMsg.obj = fftTime;
        mHandler.sendMessage(fftMsg);

        Message mixMsg = Message.obtain(mHandler);
        mixMsg.what = TYPE_MIX;// 2
        mixMsg.obj = mixTime;
        mHandler.sendMessage(mixMsg);
    }

    static{
        System.loadLibrary("JNITest");
    }

    private void testFFT_Float(){

        float[] real = Arrays.copyOf(floatData,N);
        float[] img = new float[N];

        fftTime = Algorithm.FFT_float_JNI(N,real,img);
    }

    private void testFFT_Int(){
        int[] real = Arrays.copyOf(intData,N);
        int[] img = new int[N];

        fftTime = Algorithm.FFT_int_JNI(N,real,img);
    }

    private void testMix_Float(){
        float[] data = Arrays.copyOf(floatData,N);

        mixTime = Algorithm.mix_float_JNI(data);
    }

    private void testMix_Int(){
        int[] data = Arrays.copyOf(intData,N);

        mixTime = Algorithm.mix_int_JNI(data);
    }
}
