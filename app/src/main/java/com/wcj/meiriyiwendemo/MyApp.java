package com.wcj.meiriyiwendemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class MyApp extends Application {

    private static Context mContext;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        handler=new Handler(getMainLooper());
    }

    public static Context getMyAppContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return handler;
    }
}
