package com.yannischeng.police.ui.login.model;


import com.yannischeng.police.base.BaseBeanObj;
import com.yannischeng.police.base.BaseModelResponse;
import com.yannischeng.police.base.BaseObservableSet;
import com.yannischeng.police.bean.CheckCodeBean;
import com.yannischeng.police.net.ServiceManager;
import com.yannischeng.police.net.ServicesAPI;

import rx.Observable;
import rx.functions.Func1;

/**
 * LoginModel 登录模块 被观察者数据获取
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public class LoginModel extends BaseObservableSet implements ILoginModel {

    private ServicesAPI mServicesAPI;

    public LoginModel() {
        mServicesAPI = ServiceManager.getInstance().create(ServicesAPI.class);
    }

    /**
     * 此函数作用：被观察者获取数据 -> 对数据进行转换，从数据集合中提取专一数据对象
     *
     * @param code url
     * @param baseModelResponse 请求数据后的返回结果
     * @return 返回CheckCodeBean对象
     */
    @Override
    public Observable<CheckCodeBean> getCheckCodeData(String code, final BaseModelResponse baseModelResponse) {

        // 请求数据进度条
        baseModelResponse.startRequestView();

        return myObservable(mServicesAPI.checkCode(code)).map(new Func1<BaseBeanObj<CheckCodeBean>, CheckCodeBean>() {
            @Override
            public CheckCodeBean call(BaseBeanObj<CheckCodeBean> checkCodeBeanBaseBeanObj) {

                if (checkCodeBeanBaseBeanObj.getCode() == 100) {
                    // 取消请求进度条
                    baseModelResponse.resultCancel();
                    return checkCodeBeanBaseBeanObj.getData();
                } else {
                    baseModelResponse.resultCancel();
                    // 请求结果出现异常
                    baseModelResponse.resultEndError(checkCodeBeanBaseBeanObj.getMessage());
                    return null;
                }
            }
        });
    }
}
