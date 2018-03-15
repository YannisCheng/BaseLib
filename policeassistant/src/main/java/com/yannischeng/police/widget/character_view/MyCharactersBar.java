package com.yannischeng.police.widget.character_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.yannischeng.guolvtj.R;


/**
 * Created by wenjia Cheng on 2017/9/11.
 * e-mail:cwj1714@163.com
 *
 * MyCharactersBar：A-Z侧边控件
 */
public class MyCharactersBar extends CardView {

    private static final String TAG = "MyCharactersBar";
    private MyChooseCharTouchListener mTouchListener;
    private int choose = -1;
    public static String[] characters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "# "};

    private TextView mTextView;

    public MyCharactersBar(Context context) {
        super(context);
    }

    public MyCharactersBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCharactersBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置点击控件的字母时，弹窗显示被点击的字母
    public void setShowChar(TextView textView) {
        mTextView = textView;
    }

    /**
     * 重写onDraw()。绘制自定义控件
     *
     * @param canvas 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置控件总高度
        int totalHeight = getHeight();
        //设置控件总宽度
        int totalWidth = getWidth();
        //设置控件中单个字母的高度
        int singleCharacerHeight = totalHeight / characters.length;

        //log:E/MyCharactersBar: onDraw: 1949 ， 134，69
        //Log.e(TAG, "onDraw: " + totalHeight + " ， " + totalWidth + "，" + singleCharacerHeight);


        //设置画笔
        Paint mPaint = new Paint();

        //设置单个字母的样式
        for (int i = 0; i < characters.length; i++) {
            initPaint(mPaint);
            /*if (i == choose) {
                // 选中的状态
                mPaint.setColor(Color.parseColor("#3399ff"));
                mPaint.setFakeBoldText(true);
            }*/

            //控件中单个字符的位置摆放具体设置
            float charPositionWidth = totalWidth / 2 - mPaint.measureText(characters[i]) / 2;
            float charPositionHeight = singleCharacerHeight * i + singleCharacerHeight;

            //使用初始化过得Pain，根据单个字母位置的具体设定，初始化单个字母的绘制
            setBackgroundResource(R.drawable.sidebar_background_untouch);
            canvas.drawText(characters[i], charPositionWidth, charPositionHeight, mPaint);
            mPaint.reset();
        }
    }

    /**
     * 重写dispatchTouchEvent，重写事件监听
     *
     * @param event 事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        int oldChoose = choose;

        //获取被点击字母的坐标位置
        float getYposition = event.getY();

        //通过使用 '百分比' 的形式，获取指定的字母

        int getChar = (int) (getYposition / getHeight() * characters.length);
        if (getChar < 27 && getChar >= 0) {
            //log:E/MyCharactersBar: dispatchTouchEvent: D
            //Log.e(TAG, "dispatchTouchEvent: " + characters[getChar] + ", " + getChar);

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    //当为对此控件进行事件处理时：
                    setBackgroundResource(R.drawable.sidebar_background_untouch);
                    choose = -1;
                    invalidate();
                    if (mTextView != null) {
                        mTextView.setVisibility(INVISIBLE);
                    }
                    break;
                default:
                    setBackgroundResource(R.drawable.sidebar_background);
                    if (choose != getChar) {
                        if (getChar >= 0 && getChar < characters.length) {
                            if (mTouchListener != null) {
                                mTouchListener.singleCharTouchListener(characters[getChar]);
                            }
                            if (mTextView != null) {
                                mTextView.setVisibility(VISIBLE);
                                mTextView.setText(characters[getChar]);
                            }
                            choose = getChar;
                            invalidate();
                        }
                    }

                    break;
            }
        }

        if (getYposition > getHeight()) {
            mTextView.setVisibility(GONE);
        }

        return true;
    }

    public void setSingleCharTouch(MyChooseCharTouchListener listener) {
        this.mTouchListener = listener;
    }


    private void initPaint(Paint paint) {
        //颜色
        //paint.setColor(Color.rgb(33, 65, 98));
        paint.setColor(getResources().getColor(R.color.login_txt));
        //字体外观
        //paint.setTypeface(Typeface.DEFAULT_BOLD);
        //锯齿
        paint.setAntiAlias(true);
        //字体大小
        paint.setTextSize(48);

    }
}
