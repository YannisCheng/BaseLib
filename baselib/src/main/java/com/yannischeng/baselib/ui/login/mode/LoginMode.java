package com.yannischeng.baselib.ui.login.mode;


import com.yannischeng.baselib.base.BaseMode;
import com.yannischeng.baselib.net.ServiceManager;
import com.yannischeng.baselib.base.BaseObservableSet;
import com.yannischeng.baselib.net.ServicesAPI;

/**
 * LoginMode  
 * 
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public class LoginMode extends BaseObservableSet implements BaseMode {

    private ServicesAPI mServicesAPI;

    public LoginMode() {
        mServicesAPI = ServiceManager.getInstance().create(ServicesAPI.class);
    }


    /*public Observable<LoginObjectBean> getLoginData(String username, String pwd) {
        return myObservable(mServicesAPI.getLogin(username, pwd)).map(new Func1<BaseBeanObj<LoginObjectBean>, LoginObjectBean>() {
            @Override
            public LoginObjectBean call(BaseBeanObj<LoginObjectBean> loginObjectBaseBeanObj) {

                if (loginObjectBaseBeanObj.isSuccess()) {
                    return loginObjectBaseBeanObj.getObj();
                }
                return null;
            }
        });
    }*/
}
