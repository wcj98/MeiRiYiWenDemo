package com.wcj.meiriyiwendemo.mvpimpl;

import android.util.Log;

import com.wcj.meiriyiwendemo.base.BasePresenter;
import com.wcj.meiriyiwendemo.bean.ArticleBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ArticlePresenter<V extends ArticleView> extends BasePresenter<V> {
    private static final String TAG = ArticlePresenter.class.getSimpleName();
    //提取每一段的内容
    private static final String regex = "(?<=<p>).*?(?=</p>)";
    private List<String> paras;

    private ArticleModel mArticleModel;

    {
        Log.d(TAG, "instance initializer: ");
        mArticleModel = new ArticleModelImpl();
        paras = new ArrayList<>();
    }

    /**
     * 加载今天的文章
     */
    public void loadTodayArticle() {

        if (wf.get() != null) {
            wf.get().startLoad();
            if (mArticleModel != null) {
                mArticleModel.loadTodayArticle(new ArticleModel.Callback<ArticleBean>() {
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        String content = getContent(articleBean);
                        wf.get().loadSuccess(articleBean, content);
                        wf.get().showToast("加载成功");

                    }
                    @Override
                    public void onFailed(String errorMessage) {
                        if (wf.get() != null) {
                            wf.get().loadFailed();
                            wf.get().showToast("加载失败");
                        }
                    }
                });
            }

        }
    }

    @NotNull
    private String getContent(ArticleBean articleBean) {
        String content = articleBean.getData().getContent();
        Matcher matcher = Pattern.compile(regex).matcher(content);
        //清除上一次的内容
        paras.clear();
        while (matcher.find()) {
            String group = matcher.group();
            paras.add(group);
        }
        StringBuilder sb = new StringBuilder();

        for (String s : paras) {
            if(s.length()<10){
                sb.append(s).append("\r\n");
            }else {
                sb.append("\t\t").append(s).append("\r\n");
            }

        }
        return sb.toString();
    }

    /**
     * 加载前一天或后一天的文章
     */
    public void loadPrevOrNextDayArticle(String date) {

            wf.get().startLoad();
            if (mArticleModel != null) {

                mArticleModel.loadPrevOrNextDayArticle(date,new ArticleModel.Callback<ArticleBean>() {
                    @Override
                    public void onSuccess(ArticleBean articleBean) {
                        String content = getContent(articleBean);
                        wf.get().loadSuccess(articleBean,content);
                        wf.get().showToast("加载成功");

                    }

                    @Override
                    public void onFailed(String errorMessage) {
                            wf.get().loadFailed();
                            wf.get().showToast("加载失败");
                    }
                });
            }

        }
    }


