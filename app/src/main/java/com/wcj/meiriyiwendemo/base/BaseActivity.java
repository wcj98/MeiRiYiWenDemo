package com.wcj.meiriyiwendemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V extends IView,P extends BasePresenter<V>> extends AppCompatActivity implements IView {
    protected P basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        basePresenter = getPresenter();
        if (basePresenter != null)
            basePresenter.attachView((V) this);
        init();
    }

    protected abstract int getLayoutResId();

    protected abstract void init();

    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (basePresenter != null)
            basePresenter.detachView();
    }
}
