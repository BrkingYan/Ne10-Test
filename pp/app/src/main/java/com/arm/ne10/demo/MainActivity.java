package com.arm.ne10.demo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arm.ne10.demo.R;

public class MainActivity extends AppCompatActivity {

    public class MyJavaInterface {
        MainActivity mParent;
        MyJavaInterface(MainActivity parent) {
            mParent = parent;
        }
        @JavascriptInterface
        public String NE10RunTest() {
            Log.d("tag","method run");
            return mParent.NE10RunTest();
        }
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.addJavascriptInterface(new MyJavaInterface(this), "MyJavaClass");

        myWebView.loadUrl("file:///android_asset/demo.html");*/
        String s  = NE10RunTest();
        TextView tv = findViewById(R.id.testView);
        tv.setText(s);
        Log.d("tag","test result:\n" + s);
        //String ss  = NE10RunTest();
        //Log.d("tag","test result:\n" + ss);
        //ScrollView view = findViewById(R.id.testView);

    }

    /* A native method that is implemented by native library, which is packaged
     * with this application.
     */
    public native String NE10RunTest();

    /* this is used to load the native library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.demo/lib/libNE10_test_demo.so at
     * installation time by the package manager.
     * it's under libs/armeabi/ directory under project dir
     */
    static {
        System.loadLibrary("NE10_test_demo");
    }
}
