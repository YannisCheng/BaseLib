package com.yannischeng.police.base;

/** 
 * BaseView MVP框架 View基类
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public interface BaseView {

    /**
     * 显示加载view
     * @param title 加载文字提示
     */
    void startLoading(String title);

    /**
     * 停止加载view
     */
    void stopLoading();

    /**
     * 显示异常View
     * @param msg 异常信息
     */
    void showError(String msg);

}
