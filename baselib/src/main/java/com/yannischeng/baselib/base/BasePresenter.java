package com.yannischeng.baselib.base;

/** 
 * BasePresenter  
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public abstract class BasePresenter<M, V> {
    public M mMode;
    public V mView;

    public void setVM(V view) {
        this.mView = view;
    }

}
