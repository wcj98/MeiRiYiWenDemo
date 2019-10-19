package com.wcj.meiriyiwendemo.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.wcj.meiriyiwendemo.MyApp;


public class ToastUtils {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getMyAppContext(), content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }
}
