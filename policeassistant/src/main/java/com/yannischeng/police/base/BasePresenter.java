package com.yannischeng.police.base;

import android.content.Context;

/**
 * BasePresenter  MVP框架 Presenter基类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public abstract class BasePresenter<M, V> {

    public Context mContext;
    public M mModel;
    public V mView;

    /**
     * 初始化 View所在的Activity，及 Context 当前类本身
     *
     * @param view View所在的Activity
     * @param context 当前类本身
     */
    public void setView(V view, Context context) {
        mContext = context;
        this.mView = view;
    }

}
