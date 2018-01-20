package com.yannischeng.baselib.view.recycler_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.yannischeng.guolvtj.R;
import com.zhy.autolayout.AutoRelativeLayout;



/**
 * RefreshHeadView
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/5
 */
public class RefreshHeadView extends AutoRelativeLayout implements SwipeRefreshTrigger, SwipeTrigger {
    //private ProgressBar mProgressBar;
    private TextView mTextView;

    public RefreshHeadView(Context context) {
        super(context);
    }

    public RefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取view
        LayoutInflater.from(context).inflate(R.layout.recycelr_view_head_layout, this, true);

        //获取控件
        //mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_head);
        mTextView = (TextView) findViewById(R.id.item_head);
    }

    public void setTextColor(int color){
        mTextView.setTextColor(color);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onPrepare() {//1
        mTextView.setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                mTextView.setText("释放后开始刷新");//
                mTextView.setText("正在刷新数据");//释放后开始加载
                //mProgressBar.setVisibility(VISIBLE);
            } else {
                //mProgressBar.setVisibility(GONE);
            }
        } else {
        }
    }

    @Override
    public void onRelease() {
        //mProgressBar.setVisibility(VISIBLE);
        mTextView.setText("正在刷新数据");
    }

    @Override
    public void onComplete() {
        //mProgressBar.setVisibility(GONE);
        mTextView.setText("刷新成功");
    }

    @Override
    public void onReset() {
        mTextView.setText("");
    }
}
