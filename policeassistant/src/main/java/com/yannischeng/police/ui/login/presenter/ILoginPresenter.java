package com.yannischeng.police.ui.login.presenter;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/13
 */
public interface ILoginPresenter {

    /**
     * 获取数据
     * @param code 请求url
     */
    void returnCheckCodeData(String code);
}
