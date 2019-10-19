package com.wcj.meiriyiwendemo.mvpimpl;

import com.wcj.meiriyiwendemo.base.IView;

public interface ArticleView<A> extends IView {

    void startLoad();

    void loadSuccess(A a,String content);

    void loadFailed();

    void showToast(String s);
}
