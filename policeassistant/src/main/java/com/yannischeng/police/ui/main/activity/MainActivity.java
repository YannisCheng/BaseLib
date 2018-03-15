package com.yannischeng.police.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.githang.statusbar.StatusBarCompat;
import com.yannischeng.guolvtj.R;
import com.yannischeng.police.adapter.MainViewPageAdapter;
import com.yannischeng.police.base.BaseActivity;
import com.yannischeng.police.ui.main.fragment.HomeFragment;
import com.yannischeng.police.ui.main.fragment.LineFragment;
import com.yannischeng.police.ui.main.fragment.NetBussniesFragment;
import com.yannischeng.police.ui.main.fragment.PersonalFragment;
import com.yannischeng.police.utils.PermissionUtil;

import butterknife.ButterKnife;

/**
 * MainActivity
 *
 * 1.主题颜色的修改
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/27
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    MainViewPageAdapter mViewPageAdapter;

    //@BindView(R.id.navigation)
    BottomNavigationBar mNavigationBar;
    //@BindView(R.id.main_view_pager)
    ViewPager mMainViewPager;
    //@BindView(R.id.main_tool_bar)
    Toolbar mMainToolBar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mNavigationBar = findViewById(R.id.navigation);
        mMainViewPager = findViewById(R.id.main_view_pager);
        mMainToolBar = findViewById(R.id.main_tool_bar);
        //设置此toolbar后，再设置其他属性
        setSupportActionBar(mMainToolBar);

        //初始化适配器
        initAdapter();

        //初始化BottomNavigationBar
        initBottomNavigationBar();

        // 监听 BottomNavigationBar
        bottomNavBarClick();

        //viewpager监听事件
        viewPagerClick();

        // 权限处理
        dealPermission();
    }

    @Override
    public void netError() {

    }

    @Override
    public void netOK() {

    }

    private void initAdapter() {
        mViewPageAdapter = new MainViewPageAdapter(getSupportFragmentManager());

        mViewPageAdapter.addFragment(new HomeFragment());
        mViewPageAdapter.addFragment(new NetBussniesFragment());
        mViewPageAdapter.addFragment(new LineFragment());
        mViewPageAdapter.addFragment(new PersonalFragment());

        mMainViewPager.setAdapter(mViewPageAdapter);
        mMainViewPager.setCurrentItem(0);
    }

    private void initBottomNavigationBar() {
        mNavigationBar.clearAll();
        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        mNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.title_home)
                .setInactiveIconResource(R.drawable.ic_home_white_24dp)
                .setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.title_basiness_hall)
                        .setInactiveIconResource(R.drawable.ic_home_white_24dp)
                        .setActiveColorResource(R.color.Green_700))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.title_line)
                        .setInactiveIconResource(R.drawable.ic_home_white_24dp)
                        .setActiveColorResource(R.color.Cyan_700))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, R.string.title_personal)
                        .setInactiveIconResource(R.drawable.ic_home_white_24dp)
                        .setActiveColorResource(R.color.Deep_Orange_700))
                .setAnimationDuration(100)
                .initialise();
    }

    private void bottomNavBarClick() {
        mNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mMainViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


    private void viewPagerClick() {
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigationBar.selectTab(position);
                if (position == 0) {
                    mMainToolBar.setTitle(R.string.title_home);
                    //统一修改颜色
                    mMainToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    StatusBarCompat.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.colorPrimaryDark));
                } else if (position == 1) {
                    mMainToolBar.setTitle(R.string.title_basiness_hall);
                    mMainToolBar.setBackgroundColor(getResources().getColor(R.color.Green_700));
                    StatusBarCompat.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.Green_900));
                } else if (position == 2) {
                    mMainToolBar.setTitle(R.string.title_line);
                    mMainToolBar.setBackgroundColor(getResources().getColor(R.color.Cyan_700));
                    StatusBarCompat.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.Cyan_900));
                } else if (position == 3) {
                    mMainToolBar.setTitle(R.string.title_personal);
                    mMainToolBar.setBackgroundColor(getResources().getColor(R.color.Deep_Orange_700));
                    StatusBarCompat.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.Deep_Orange_900));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 权限处理
     */
    private void dealPermission() {
        // ACCESS_FINE_LOCATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, PermissionUtil.STR_PERMISSION_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{PermissionUtil.STR_PERMISSION_LOCATION, PermissionUtil.STR_PERMISSION_STORAGE,PermissionUtil.STR_PERMISSION_FINE_LOCATION}, PermissionUtil.CODE_PERMISSION_LOCATION);
            } else {
                // 权限已经通过
                //doOK();
            }
        } else {
            // 系统版本 < 23
            //doOK();
        }
    }


    /**
     * 权限调用结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 0 OK, -1 NO
        switch (requestCode) {
            case PermissionUtil.CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //doOK();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[0])) {
                        // 首先判断是否需要弹出窗口
                        showDialog(MainActivity.this,new String[]{PermissionUtil.STR_PERMISSION_LOCATION}, PermissionUtil.CODE_PERMISSION_LOCATION);
                    } else {
                        // 如果用户坚持不授权，那么就直接退出。
                        finish();
                    }

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[1])) {
                        // 首先判断是否需要弹出窗口
                        showDialog(MainActivity.this,new String[]{PermissionUtil.STR_PERMISSION_STORAGE}, PermissionUtil.CODE_PERMISSION_STORAGE);
                    } else {
                        // 如果用户坚持不授权，那么就直接退出。
                        finish();
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[2])) {
                        // 首先判断是否需要弹出窗口
                        showDialog(MainActivity.this,new String[]{PermissionUtil.STR_PERMISSION_FINE_LOCATION}, PermissionUtil.CODE_PERMISSION_STORAGE);
                    } else {
                        // 如果用户坚持不授权，那么就直接退出。
                        finish();
                    }
                }
            default:
        }
    }

    private void showDialog(final Activity activity, final String[] requestPermission, final int requestCode) {
        showMessageOKCancel(activity, "提示信息", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 授权
                dialog.dismiss();
                requestPermissions(requestPermission,requestCode);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取消
                dialog.dismiss();
                // 直接finish异常！！
                finish();
            }
        });
    }

    /**
     * 执行弹窗
     */
    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListerer) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("授权", okListener)
                .setNegativeButton("取消", noListerer)
                .create()
                .show();
    }

}
