package com.wcj.meiriyiwendemo.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IView> {

    protected WeakReference<V> wf;

    public void attachView(V v){
        wf=new WeakReference<>(v);
    }

    public void detachView(){
        if(wf.get()!=null){
            wf.clear();
            wf=null;
        }
    }
}
