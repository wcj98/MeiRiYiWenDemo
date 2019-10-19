package com.wcj.meiriyiwendemo.mvpimpl;

import com.wcj.meiriyiwendemo.base.IModel;

public interface ArticleModel<A> extends IModel {

    //加载今天的文章
    void loadTodayArticle(Callback<A> callback);

    //加载前一天或后一天的文章
    void loadPrevOrNextDayArticle(String date, Callback<A> callback);

    interface Callback<A> {
        //成功的回调
        void onSuccess(A a);

        //失败的回调
        void onFailed(String errorMessage);
    }




}

