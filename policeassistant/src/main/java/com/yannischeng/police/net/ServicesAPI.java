package com.yannischeng.police.net;

import com.yannischeng.police.base.BaseBeanObj;
import com.yannischeng.police.bean.CheckCodeBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ServicesAPI 数据请求API集合
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public interface ServicesAPI {

    /**
     * BASE_URL 基础URL
     */
    public final static String BASE_URL = "http://117.78.47.212:9000/bczz/";

    /**
     * 测试，无实际意义
     */
    public final static String TestUrl = "http://xwqy.zhihuixlf.com/public/mobile_api/home_user/";

    @POST("checkCode")
    Observable<BaseBeanObj<CheckCodeBean>> checkCode(
            @Query("code") String code
    );
    // http://xwqy.zhihuixlf.com/public/mobile_api/home_user/checkCode?code=123123
    // http://xwqy.zhihuixlf.com/public/mobile_api/home_user/checkCode?code=123123
}
