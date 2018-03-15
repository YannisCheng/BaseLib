package com.yannischeng.police.base;

/**
 * BaseModelResponse  MVP框架 Model获取数据（被观察者）状态处理
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/13
 */
public interface BaseModelResponse {
    /**
     * 开始请求数据的状态
     */
    void startRequestView();

    /**
     * 请求数据结束 -- 存在异常
     * @param msg 异常信息
     */
    void resultEndError(String msg);

    /**
     * 请求数据结束 -- 正常
     */
    void resultCancel();

}
