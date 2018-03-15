package com.yannischeng.police.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.yannischeng.police.utils.AppManager;


/**
 * BaseActivity Activity基类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 *
 * revise in 2018-03-13 09:12:02
 * 删除多余功能，仅保留实际功能
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected Context mContext;

    /**
     * Activity管理器
     */
    private AppManager appManagers;
    private long exitTime = 0;

    /**
     * 监听网络广播
     */
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;

        //初始化AppManager
        appManagers = AppManager.getAppManager();

        //将当前的Activity加入AppManager
        appManagers.addActivity(this);

        //监听网络状态
        registerBroadrecevicer();
    }

    /**
     * 注册网络状态广播
     */
    private void registerBroadrecevicer() {
        //获取广播意图
        receiver = new NetBroadcastReceiver();
        //创建监听意图
        IntentFilter filter = new IntentFilter();
        //添加动作，监听网络
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

    }

    /**
     * 自定义网络状态广播接收器
     */
    class NetBroadcastReceiver extends BroadcastReceiver {

        private ConnectivityManager mConnectivityManager;
        private NetworkInfo mNetworkInfo;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    netOK();
                } else {
                    netError();
                }
            }
        }
    }

    /**
     * 自定义网络状态广播接收器 回调方法  连接失败
     */
    public abstract void netError();

    /**
     * 自定义网络状态广播接收器 回调方法  连接成功
     */
    public abstract void netOK();

    /**
     * 关闭当前Activity
     */
    public void killActivity(Activity activity) {
        Log.e(TAG, "killActivity: " + activity);
        appManagers.finishActivity(activity);
    }


    /**
     * 点击两次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // killActivity(this);
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出当前App - 方法
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            appManagers.appExit(this);
        }
    }

    /**
     * 取消广播的注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
