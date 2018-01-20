package com.yannischeng.baselib.ui.login.presenter;


import com.yannischeng.baselib.base.BasePresenter;
import com.yannischeng.baselib.ui.login.mode.LoginMode;
import com.yannischeng.baselib.ui.login.view.LoginView;

/**
 * LoginPresenter  
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public class LoginPresenter extends BasePresenter<LoginMode, LoginView> {

    private static final String TAG = "LoginPresenter";

    private void initMV() {
        mMode = new LoginMode();
    }


    /*public void returnLoginData(String username, String pwd) {
        initMV();
        mMode.getLoginData(username, pwd).subscribe(new Subscriber<LoginObjectBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Log.e(TAG, "onError: " + e.toString());
                mView.showErrorTip(e);
            }

            @Override
            public void onNext(LoginObjectBean loginObjectBean) {
                //Log.e(TAG, "onNext: " + loginObjectBean.toString());
                mView.userLoginData(loginObjectBean);
            }
        });
    }*/
}
