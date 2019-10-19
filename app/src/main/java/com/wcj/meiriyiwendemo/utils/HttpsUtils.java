package com.wcj.meiriyiwendemo.utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wcj.meiriyiwendemo.bean.ArticleBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsUtils {
    private static final String TAG = HttpsUtils.class.getSimpleName();
    private static final String TODAY_URL = "today";
    private static final String BASE_URL = "https://interface.meiriyiwen.com/article/";
    private static final String QUERY_URL = "day/?day=";

    public static String httpsGet(String date) {
        String path;
        BufferedReader bufferedReader = null;
        if (TextUtils.isEmpty(date)) {
            path = BASE_URL + TODAY_URL;
        } else {
            path = BASE_URL + QUERY_URL + date;
        }
        Log.d(TAG, "url: " + path);
        try {
            URL url = new URL(path);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
