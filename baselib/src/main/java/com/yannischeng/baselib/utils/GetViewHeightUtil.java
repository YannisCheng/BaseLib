package com.yannischeng.baselib.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by wenjia Cheng on 2017/10/11.
 * e-mail:cwj1714@163.com
 *
 * 工具类 - 获取布局控件的高度
 */

public class GetViewHeightUtil {

    /**
     * 获取 GridView 的总高度
     */
    public void serGridViewHeight(GridView gridLayout) {
        //获取适配器
        ListAdapter listAdapter = gridLayout.getAdapter();
        if (listAdapter == null) {
            return;
        }

        //获取列数
        int columnNum = gridLayout.getNumColumns();
        //定义总高度
        int totalHeight = 0;

        //获取每行高度之和
        for (int i = 0; i < gridLayout.getCount(); i += columnNum) {
            //获取每一个item的参数
            View itemView = listAdapter.getView(1, null, gridLayout);
            itemView.measure(0, 0);
            //获取高度之和
            totalHeight += itemView.getMeasuredHeight();
        }

        //获取GridView控件的布局属性
        ViewGroup.LayoutParams params = gridLayout.getLayoutParams();
        //将总高度设置为控件的总高度
        params.height = totalHeight + 32;
        //设置数值
        gridLayout.setLayoutParams(params);
    }

    /**
     * 获取ListView中的总行数的总高度
     */
    public void setListViewHeight(ListView mShowContent) {
        int itemHeight = 120;
        int totalHeight = 0;

        ListAdapter listAdapter = mShowContent.getAdapter();

        if (listAdapter == null) {
            return;
        }

        for (int t = 0; t < listAdapter.getCount(); t++) {
            totalHeight += itemHeight + mShowContent.getDividerHeight();
        }

        ViewGroup.LayoutParams layoutParams = mShowContent.getLayoutParams();
        layoutParams.height = totalHeight;
        mShowContent.setLayoutParams(layoutParams);
    }
}
