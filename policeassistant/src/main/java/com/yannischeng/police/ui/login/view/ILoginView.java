package com.yannischeng.police.ui.login.view;


import com.yannischeng.police.base.BaseView;
import com.yannischeng.police.bean.CheckCodeBean;

/**
 * ILoginView
 *
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/11/28
*/
public interface ILoginView extends BaseView {

    /**
     * 将获取的CheckCodeBean对象填充至view界面
     * @param checkCodeBean 数据对象
     */
    void userLoginDataView(CheckCodeBean checkCodeBean);


}
