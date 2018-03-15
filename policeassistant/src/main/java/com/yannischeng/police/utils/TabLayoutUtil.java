package com.yannischeng.police.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.yannischeng.guolvtj.R;

import java.lang.reflect.Field;

/**
 * Created by wenjia Cheng on 2017/9/10.
 * e-mail:cwj1714@163.com
 * <p>
 * TabLayout 额外设置
 * <p>
 * 1-设置指示器宽度
 * 2-设置tab标签分割线
 */

public class TabLayoutUtil {

    private TabLayout mTabLayout;
    private Context mContext;

    public TabLayoutUtil(TabLayout tabLayout, Context context) {
        mTabLayout = tabLayout;
        mContext = context;
    }

    //设置tab分割线 方法，直接调用即可
    public void setDevider() {
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        //设置 分割线 与上下之间的间隔
        linearLayout.setPadding(0, 20, 0, 20);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mContext, R.drawable.layout_divider));
    }

    //设置指示器宽度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
