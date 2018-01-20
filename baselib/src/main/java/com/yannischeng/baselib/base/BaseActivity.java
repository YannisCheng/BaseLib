package com.yannischeng.baselib.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.yannischeng.baselib.utils.AppManager;


/**
 * BaseActivity
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected Context mContext;

    private AppManager appManagers;
    private long exitTime = 0;
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
     * 1 获取当前系统的API版本号 为实现内置Activity切换动画提供基础  本app要求：api >= 21,需要res/transition/文件
     * <p>
     * 实际本判断不需要
     */
    public boolean isApiOK() {
        if (Build.VERSION.SDK_INT >= 21) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 2 内置Activity切换动画, 在setContentView()之前使用,第1个界面和第2个界面在相同的位置使用
     */
    public void useBeforeSetConentView() {
        if (isApiOK()) {
            //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
    }

    /**
     * 3 内置Activity切换动画
     *
     * @param intent 切换的Activity
     */
    public void startActivityMy(Intent intent) {
        if (isApiOK()) {
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }

    }

    /**
     * 4 在 oncreate()中使用
     *
     * @param resourceId transiton 中的文件，在第2个Activity中使用
     */
    public void setSecondActivityTag(int resourceId) {
        if (isApiOK()) {
            //Transition slide = TransitionInflater.from(this).inflateTransition(resourceId/*R.transition.slide*/);
            //getWindow().setExitTransition(slide);
            //getWindow().setEnterTransition(slide);
        }

    }

    /**
     * 注册网络状态广播
     */
    private void registerBroadrecevicer() {
        //获取广播意图
        receiver = new NETBroadcastReceiver();
        //创建监听意图
        IntentFilter filter = new IntentFilter();
        //添加动作，监听网络
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

    }

    /**
     * 自定义网络状态广播接收器
     */
    private class NETBroadcastReceiver extends BroadcastReceiver {

        private ConnectivityManager mConnectivityManager;
        private NetworkInfo mNetworkInfo;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    connectOK();
                } else {
                    connectError();
                }
            }
        }
    }

    /**
     * 自定义网络状态广播接收器 回调方法  连接失败
     */
    public abstract void connectError();

    /**
     * 自定义网络状态广播接收器 回调方法  连接成功
     */
    public abstract void connectOK();

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
            killActivity(this);
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
