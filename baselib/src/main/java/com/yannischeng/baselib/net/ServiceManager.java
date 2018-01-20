package com.yannischeng.baselib.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ServiceManager ： 数据请求管理
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class ServiceManager {

    private static final String TAG = "RetrofitServiceManager";

    /**
     * READ_TIME_OUT : 读超时长，单位：毫秒
     */
    public static final int READ_TIME_OUT = 10000;

    /**
     * CONNECT_TIME_OUT : 连接时长，单位：毫秒
     */
    public static final int CONNECT_TIME_OUT = 10000;

    private Retrofit mRetrofit;

    /**
     * 初始化 RetrofitServiceManager 类
     */
    private ServiceManager() {

        //增加请求头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };

        // 待整理：添加公共参数拦截器

        //配置 OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(headerInterceptor)
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        //创建Retrofit
        mRetrofit = new Retrofit.Builder()
                //添加OkHttpClient配置
                .client(client)
                //添加Retrofit适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //添加转换器Converter(将json 转为JavaBean)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //添加BaseUrl
                .baseUrl(BaseUrl.BASE_URL)
                .build();
    }

    /**
     * 内部类实现 - 单例模式
     */
    private static class SingletonHolder {
        private static ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return SingletonHolder.instance;
    }


    /**
     * 创建 接口实例
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
