package com.yannischeng.police.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.amitshekhar.DebugDB;


/**
 * BaseApplication Application基类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor editor;

    private static BaseApplication sBaseApplication;

    /**
     * 单一实例
     */
    public static BaseApplication getInstance() {
        return sBaseApplication;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        sBaseApplication = this;

        //初始化本地存储
        mSharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        //在网页中查看本地存储数据库
        DebugDB.getAddressLog();
    }


}
