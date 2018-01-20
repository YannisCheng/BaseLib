package com.yannischeng.baselib.base;

/** 
 * BaseView  
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public interface BaseView {

    void showLoading(String title);

    void stopLoading();

    void showErrorTip(Throwable e);

}
