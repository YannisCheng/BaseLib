package com.yannischeng.police.ui.login.model;

import com.yannischeng.police.base.BaseModel;
import com.yannischeng.police.base.BaseModelResponse;
import com.yannischeng.police.bean.CheckCodeBean;

import rx.Observable;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/13
 */
public interface ILoginModel extends BaseModel{

    Observable<CheckCodeBean> getCheckCodeData(String code, BaseModelResponse baseModelResponse);
}
