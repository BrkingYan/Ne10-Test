package com.example.yy.computes;

public class Algorithm {
    public native static double FFT_int_JNI(int N,int[] real,int[] img);

    public native static double FFT_float_JNI(int N,float[] real,float[] img);

    public native static double mix_int_JNI(int[] data);

    public native static double mix_float_JNI(float[] data);


}
