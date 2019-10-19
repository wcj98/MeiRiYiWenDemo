package com.wcj.meiriyiwendemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class UILoader extends FrameLayout {

    private View mLoadView;
    private View mFailedView;
    private View mSuccessView;
    private OnClickReLoadListener mOnClickReLoadListener;

    public void setOnClickReLoadListener(OnClickReLoadListener mOnClickReLoadListener) {
        this.mOnClickReLoadListener = mOnClickReLoadListener;
    }

    public enum UIStatus {
        LOADING, LOADING_FAILED, LOADING_SUCCESS, NONE;
    }

    private UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        switchViewByStatus(mCurrentStatus);
    }

    private void switchViewByStatus(final UIStatus mCurrentStatus) {
        if (mLoadView == null) {
            mLoadView = getLoadView();
            addView(mLoadView);
        }
        mLoadView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);
        if (mFailedView == null) {
            mFailedView = getFailedView();
            mFailedView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnClickReLoadListener != null) {
                        mOnClickReLoadListener.reload();
                    }
                }
            });
            addView(mFailedView);
        }
        mFailedView.setVisibility(mCurrentStatus == UIStatus.LOADING_FAILED ? VISIBLE : GONE);

        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.LOADING_SUCCESS ? VISIBLE : GONE);
    }

    protected abstract View getSuccessView(UILoader uiLoader);

    protected View getFailedView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.layut_loading_failed, this, false);
    }

    protected View getLoadView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.layut_loading, this, false);
    }

    public void updateStatus(final UIStatus mCurrentStatus) {
        this.mCurrentStatus = mCurrentStatus;
        //主线程
        MyApp.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchViewByStatus(mCurrentStatus);
            }
        });


    }

   public interface OnClickReLoadListener {
        void reload();
    }


}
