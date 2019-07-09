package com.example.yy.computes;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.Callable;

public class NeonUtil implements Runnable {


    private String result;
    private Handler mHandler;

    public NeonUtil(String libName,Handler handler){
        System.loadLibrary(libName);
        mHandler = handler;
    }

    public static native String NE10RunTest();


    @Override
    public void run() {
        result = NE10RunTest();
        Message neonMsg = Message.obtain(mHandler);
        neonMsg.what = 3;
        neonMsg.obj = result;
        mHandler.sendMessage(neonMsg);
    }

}
