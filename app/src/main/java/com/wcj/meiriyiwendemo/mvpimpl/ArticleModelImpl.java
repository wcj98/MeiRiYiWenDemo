package com.wcj.meiriyiwendemo.mvpimpl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wcj.meiriyiwendemo.bean.ArticleBean;
import com.wcj.meiriyiwendemo.utils.HttpsUtils;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ArticleModelImpl implements ArticleModel<ArticleBean> {

    private Disposable mDisposable;

    @Override
    public void loadTodayArticle(final Callback<ArticleBean> callback) {
        if (callback == null) {
            return;
        }

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String data = HttpsUtils.httpsGet("");
                if (data == null) {
                    callback.onFailed("请求失败");
                    return;
                }
                emitter.onNext(data);
            }
        }).map(new Function<String, ArticleBean>() {
            @Override
            public ArticleBean apply(String s) throws Exception {
                Gson gson = new GsonBuilder().serializeNulls().create();
                return gson.fromJson(s, ArticleBean.class);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        callback.onSuccess(articleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void loadPrevOrNextDayArticle(final String date, final Callback<ArticleBean> callback) {
        if (callback == null) {
            return;
        }

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String data = HttpsUtils.httpsGet(date);
                if (data == null) {
                    callback.onFailed("请求失败");
                    return;
                }
                emitter.onNext(data);
            }
        }).map(new Function<String, ArticleBean>() {
            @Override
            public ArticleBean apply(String s) throws Exception {
                Gson gson = new GsonBuilder().serializeNulls().create();
                return gson.fromJson(s, ArticleBean.class);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        callback.onSuccess(articleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
