package com.wcj.meiriyiwendemo.mvpimpl;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wcj.meiriyiwendemo.R;
import com.wcj.meiriyiwendemo.UILoader;
import com.wcj.meiriyiwendemo.base.BaseActivity;
import com.wcj.meiriyiwendemo.base.BasePresenter;
import com.wcj.meiriyiwendemo.bean.ArticleBean;
import com.wcj.meiriyiwendemo.utils.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ArticleActivity extends BaseActivity implements ArticleView<ArticleBean>, View.OnClickListener, UILoader.OnClickReLoadListener {

    private ArticlePresenter mArticlePresenter;
    private UILoader mULoader;
    private TextView content;
    private TextView digest;
    private TextView author;
    private TextView title;
    private ArticleBean mArticleBean;
    //加载完成
    private static final int FLAG_LOAD_COMPLETED = 0;
    //加载今日
    public static final int FLAG_LOAD_TODAY = 1;
    //加载前一天
    public static final int FLAG_LOAD_PREV = 2;
    //加载后一天
    public static final int FLAG_LOAD_NEXT = 3;
    //当前加载的标志
    private int mFlag = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article;
    }

    @Override
    protected void init() {
        FrameLayout content_holder = findViewById(R.id.content_holder);
        Button btn_curr = findViewById(R.id.btn_curr);
        btn_curr.setOnClickListener(this);
        Button btn_prev = findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(this);
        Button btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        mULoader = new UILoader(this) {
            @Override
            protected View getSuccessView(UILoader uiLoader) {
                View inflate = LayoutInflater.from(uiLoader.getContext()).inflate(R.layout.layut_loading_success, uiLoader, false);
                title = inflate.findViewById(R.id.tv_article_title);
                author = inflate.findViewById(R.id.tv_article_author);
                digest = inflate.findViewById(R.id.tv_article_digest);
                content = inflate.findViewById(R.id.tv_article_content);
                return inflate;
            }
        };

        mULoader.setOnClickReLoadListener(this);
        content_holder.addView(mULoader);
        mFlag = FLAG_LOAD_TODAY;
        mULoader.updateStatus(UILoader.UIStatus.LOADING);
        mArticlePresenter.loadTodayArticle();

    }

    @Override
    protected BasePresenter getPresenter() {
        mArticlePresenter = new ArticlePresenter();
        return mArticlePresenter;
    }

    @Override
    public void startLoad() {
        mULoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void loadSuccess(ArticleBean articleBean, String content) {
        mFlag = FLAG_LOAD_COMPLETED;
        mULoader.updateStatus(UILoader.UIStatus.LOADING_SUCCESS);
        mArticleBean = articleBean;
        this.author.setText(articleBean.getData().getAuthor());
        this.digest.setText(articleBean.getData().getDigest());
        this.title.setText(articleBean.getData().getTitle());
        this.content.setText(content);
    }


    @Override
    public void loadFailed() {
        mULoader.updateStatus(UILoader.UIStatus.LOADING_FAILED);
    }

    @Override
    public void showToast(String s) {
        ToastUtils.showToast(s);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_prev:
                if (mArticleBean != null) {
                    String prev = mArticleBean.getData().getDate().getPrev();
                    mFlag = FLAG_LOAD_PREV;
                    mArticlePresenter.loadPrevOrNextDayArticle(prev);
                }
                break;
            case R.id.btn_curr:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                String format = dateFormat.format(new Date(System.currentTimeMillis()));
                //返回的日期是一直是当前的日期的无法判断
                if (mArticleBean != null && mArticleBean.getData().getDate().getCurr().equals(format)) {
                    ToastUtils.showToast("已经是今天的文章！");
                    return;
                }
                mArticlePresenter.loadTodayArticle();
                break;
            case R.id.btn_next:
                if (mArticleBean != null) {
                    String next = mArticleBean.getData().getDate().getNext();
                    mFlag = FLAG_LOAD_NEXT;

                    mArticlePresenter.loadPrevOrNextDayArticle(next);
                }
                break;
        }
    }

    //重新加载
    @Override
    public void reload() {
        switch (mFlag) {
            case FLAG_LOAD_PREV:
                mArticlePresenter.loadPrevOrNextDayArticle(mArticleBean.getData().getDate().getPrev());
                break;
            case FLAG_LOAD_TODAY:
                mArticlePresenter.loadTodayArticle();
                break;
            case FLAG_LOAD_NEXT:
                mArticlePresenter.loadPrevOrNextDayArticle(mArticleBean.getData().getDate().getNext());
                break;
        }
    }
}
