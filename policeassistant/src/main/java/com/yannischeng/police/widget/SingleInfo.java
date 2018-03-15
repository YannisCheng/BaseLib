package com.yannischeng.police.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannischeng.guolvtj.R;
import com.yannischeng.police.utils.DpUtils;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * SingleInfo
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/29
 */
public class SingleInfo extends AutoRelativeLayout {

    private static final String TAG = "SingleInfo";

    TextView mSingleText;
    ImageView mSingleImgLeft;
    ImageView mSingleImgRight;
    AutoRelativeLayout mSingleInfoLayout;
    Context mContext;

    String title = "";
    float titleSize = 0;
    int titleColor = 0;
    int imgLeft = 0;
    int imgRight = 0;

    ClickListener mClickListener;


    public SingleInfo(Context context) {
        super(context);
    }

    public SingleInfo(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.view_single_info_layout, this, true);

        mSingleInfoLayout = view.findViewById(R.id.single_info_layout);
        mSingleText = view.findViewById(R.id.single_text);
        mSingleImgRight = view.findViewById(R.id.single_img_right);
        mSingleImgLeft = view.findViewById(R.id.single_img_left);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SingleInfo);

        if (array != null) {
            title = array.getString(R.styleable.SingleInfo_single_title);
            titleSize = array.getDimension(R.styleable.SingleInfo_single_title_size, DpUtils.px2dip(context, 18));
            titleColor = array.getColor(R.styleable.SingleInfo_single_title_color, getResources().getColor(R.color.Deep_Orange_700));

            imgLeft = array.getResourceId(R.styleable.SingleInfo_single_img_left, R.drawable.ic_launcher);
            imgRight = array.getResourceId(R.styleable.SingleInfo_single_img_right, R.drawable.ic_launcher);

            Log.e(TAG, "ヽ(｀Д´)ﾉ -> SingleInfo title : " + title + ", titleSize : " + titleSize + ", titleColor : " + titleColor + ", imgLeft :" + imgLeft + ", imgRight : " + imgRight);

            mSingleText.setText(title);
            mSingleText.setTextColor(titleColor);
            mSingleText.setTextSize(DpUtils.px2dip(context, titleSize));

            mSingleImgLeft.setBackgroundResource(imgLeft);
            mSingleImgRight.setBackgroundResource(imgRight);

            array.recycle();
        }

        mSingleInfoLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.singleOnClickListener(v);
            }
        });
    }

    public void setImgClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }


    public String getTitleInfo() {
        return title;
    }

    public void setTitleInfo(String title) {
        this.title = title;
        mSingleText.setText(title);
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
        mSingleText.setTextSize(DpUtils.px2dip(mContext, titleSize));
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        mSingleText.setTextColor(titleColor);
    }

    public int getImgLeft() {
        return imgLeft;
    }

    public void setImgLeft(int imgLeft) {
        this.imgLeft = imgLeft;
        mSingleImgLeft.setBackgroundResource(imgLeft);
    }

    public int getImgRight() {
        return imgRight;
    }

    public void setImgRight(int imgRight) {
        this.imgRight = imgRight;
        mSingleImgRight.setBackgroundResource(imgRight);
    }

    public interface ClickListener {

        void singleOnClickListener(View view);
    }
}
