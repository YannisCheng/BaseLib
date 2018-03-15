package com.yannischeng.police.ui.login.presenter;


import com.yannischeng.police.base.BaseModelResponse;
import com.yannischeng.police.base.BasePresenter;
import com.yannischeng.police.bean.CheckCodeBean;
import com.yannischeng.police.ui.login.model.LoginModel;
import com.yannischeng.police.ui.login.view.ILoginView;

import rx.Subscriber;

/**
 * LoginPresenter  
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> implements BaseModelResponse, ILoginPresenter{

    private static final String TAG = "LoginPresenter";

    public LoginPresenter() {
        mModel = new LoginModel();
    }

    /**
     * 获取数据
     * @param code url
     */
    @Override
    public void returnCheckCodeData(String code) {
        mModel.getCheckCodeData(code,this).subscribe(new Subscriber<CheckCodeBean>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CheckCodeBean checkCodeBean) {
                mView.userLoginDataView(checkCodeBean);
            }
        });
    }

    /**
     * BaseModelResponse 接口中对 数据请求过程的 view 状态变化的实现
     */
    @Override
    public void startRequestView() {
        mView.startLoading("加载中……");
    }

    @Override
    public void resultEndError(String msg) {
        mView.showError(msg);
    }

    @Override
    public void resultCancel() {
        mView.stopLoading();
    }


}
