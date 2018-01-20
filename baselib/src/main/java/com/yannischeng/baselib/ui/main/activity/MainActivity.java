package com.yannischeng.baselib.ui.main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.githang.statusbar.StatusBarCompat;
import com.yannischeng.baselib.adapter.MainViewPageAdapter;
import com.yannischeng.baselib.base.BaseActivity;
import com.yannischeng.baselib.ui.main.fragment.HomeFragment;
import com.yannischeng.baselib.ui.main.fragment.LineFragment;
import com.yannischeng.baselib.ui.main.fragment.NetBussniesFragment;
import com.yannischeng.baselib.ui.main.fragment.PersonalFragment;
import com.yannischeng.guolvtj.R;

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


    }

    @Override
    public void connectError() {

    }

    @Override
    public void connectOK() {

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


}
