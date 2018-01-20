package com.yannischeng.baselib.view.recycler_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.yannischeng.guolvtj.R;
import com.zhy.autolayout.AutoRelativeLayout;



/**
 * LoadMoreFooterView
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/5
 */
public class LoadMoreFooterView extends AutoRelativeLayout implements SwipeLoadMoreTrigger, SwipeTrigger {
    //private ProgressBar mProgressBar;
    private TextView mTextView;

    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取view
        LayoutInflater.from(context).inflate(R.layout.recyer_view_footer_layout, this, true);

        //获取控件
        //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextView = (TextView) findViewById(R.id.item_single_tv_val);
    }

    public void setTextColor(int color){
        mTextView.setTextColor(color);
    }

    @Override
    public void onLoadMore() {
        //mProgressBar.setVisibility(GONE);
        mTextView.setText("上拉加载更多数据");
    }

    @Override
    public void onPrepare() {
        mTextView.setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                //mProgressBar.setVisibility(VISIBLE);
                mTextView.setText("释放后开始加载");//
                mTextView.setText("正在加载数据");//释放后开始加载
                //mProgressBar.setVisibility(VISIBLE);
            } else {
                //mProgressBar.setVisibility(GONE);
                mTextView.setText("上拉加载更多数据");
                //mProgressBar.setVisibility(GONE);
            }
        } else {
            //setText("正在加载数据");
        }
    }


    @Override
    public void onRelease() {
        //mProgressBar.setVisibility(VISIBLE);
        mTextView.setText("正在加载数据");
        //mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onComplete() {
        //mProgressBar.setVisibility(GONE);
        mTextView.setText("加载结束");
        //mProgressBar.setVisibility(GONE);
    }

    @Override
    public void onReset() {
        mTextView.setText("");
    }
}
