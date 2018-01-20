package com.yannischeng.baselib.view.date_picker.picker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yannischeng.baselib.utils.DataDealUtil;
import com.yannischeng.baselib.view.date_picker.adapter.AbstractWheelTextAdapter1;
import com.yannischeng.baselib.view.date_picker.config.OnWheelScrollListener;
import com.yannischeng.baselib.view.date_picker.config.WheelView;
import com.yannischeng.guolvtj.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;


/**
 * Author:  yannischeng
 * Email:   cwj1714@163.com
 * Date:    2017年10月10日11:50:12
 * Description: 单日期选择器
 */
public class SinglePicker extends PopupWindow implements View.OnClickListener {

    private static final String TAG = "SinglePicker";

    private Context context;

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private TextView btnSure;
    private TextView btnCancel;

    private TextView firstTitle;

    private View lineView;
    private AutoLinearLayout mLayoutShowTime, showFour;

    private ArrayList<Integer> arry_years = new ArrayList<Integer>();
    private ArrayList<Integer> arry_months = new ArrayList<Integer>();
    private ArrayList<Integer> arry_days = new ArrayList<Integer>();

    /**
     * 年月日的适配器
     */
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;

    private String month;
    private String day;

    /**
     * 获取当前日期 选择器
     */
    private String currentYear;
    private String currentMonth;
    private String currentDay;


    /**
     * 设置字号大小
     */
    private int maxTextSize = 24;
    private int minTextSize = 18;

    /**
     * 是否初始化日期
     */
    private boolean issetdata = false;

    /**
     * 获取选择的日期 选择器
     */
    private String selectYear;
    private String selectMonth;
    private String selectDay;


    /**
     * 显示日期的个数
     */
    private int showItemNum = 8;

    private OnBirthListener onBirthListener;
    private DataDealUtil mDataDealUtil;

    /**
     * 构造函数
     */
    public SinglePicker(final Context context) {
        super(context);
        this.context = context;
        mDataDealUtil = new DataDealUtil();

        currentDay = mDataDealUtil.getDay();

        currentMonth = mDataDealUtil.getMonth();

        currentYear = mDataDealUtil.getYear();

        //界面控件初始化
        View view = View.inflate(context, R.layout.singel_date_picker, null);
        initPicker(view);
        initPopupWindow(view);

        //是否已经设定过日期
        if (!issetdata) {
            //系统当前月份的前 1 个月
            int initMonth1 = Integer.parseInt(mDataDealUtil.getMonth1());
            //系统的当前月份
            int initMonth2 = Integer.parseInt(mDataDealUtil.getMonth());
            setDate(mDataDealUtil.getYear(), String.valueOf(initMonth2), mDataDealUtil.getDay(), mDataDealUtil.getYear(), String.valueOf(initMonth2), mDataDealUtil.getDay());
        }

        initPickerData(context);

        dealYearAYear2(context);
        dealMonthAMonth2(context);
        dealDayADay2();
    }

    /**
     * 初始化 选择器 控件
     *
     * @param view 布局
     */
    private void initPicker(View view) {
        wvYear = (WheelView) view.findViewById(R.id.wv_birth_year);
        wvMonth = (WheelView) view.findViewById(R.id.wv_birth_month);
        wvDay = (WheelView) view.findViewById(R.id.wv_birth_day);

        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);
        firstTitle = (TextView) view.findViewById(R.id.first_time);
        showFour = (AutoLinearLayout) view.findViewById(R.id.ly_myinfo_changeaddress);

        lineView = view.findViewById(R.id.choose_time_line);
        mLayoutShowTime = (AutoLinearLayout) view.findViewById(R.id.choose_time_layout);

        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        dealPickerView();
    }


    /**
     * 控制双时间选择器 控件 的显隐藏
     */
    private void dealPickerView() {
        Log.e(TAG, "dealPickerView true ");
        btnSure.setBackgroundResource(R.drawable.ic_done_origion_24dp);
        btnCancel.setBackgroundResource(R.drawable.ic_clear_origion_24dp);
        firstTitle.setVisibility(View.VISIBLE);
        lineView.setVisibility(View.VISIBLE);
        mLayoutShowTime.setVisibility(View.VISIBLE);
        mLayoutShowTime.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_anim_show_bottom));
        btnCancel.setVisibility(View.VISIBLE);
        btnSure.setVisibility(View.VISIBLE);
    }


    /**
     * 初始化 popupWindow
     *
     * @param view 布局
     */
    private void initPopupWindow(View view) {
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    /**
     * 初始化 选择器的日期设定（双日期选择器）
     */
    private void initPickerData(Context context) {
        initYears();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(showItemNum);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));

        initMonths(Integer.parseInt(month));
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentMonth), maxTextSize, minTextSize);
        wvMonth.setVisibleItems(showItemNum);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));

        initDays(Integer.parseInt(day));
        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(currentDay) - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(showItemNum);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(Integer.parseInt(currentDay) - 1);
    }

    /**
     * 年 处理
     */
    private void dealYearAYear2(final Context context) {

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectYear = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                firstTitle.setText(selectYear);
                setTextviewSize(selectYear, mYearAdapter);
                currentYear = selectYear;
                //确定当前年份
                setYear(currentYear);
                //根据当前年份设置月份的集合显示
                initMonths(Integer.parseInt(month));
                mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                wvMonth.setVisibleItems(showItemNum);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);

                day = mDataDealUtil.dealDiffDays(currentYear, month);

                selectMonth = "";
                selectDay = "";

            }
        });
    }

    /**
     * 月 处理
     */
    private void dealMonthAMonth2(final Context context) {

        wvMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectMonth = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                dealSelectorMonth();

            }
        });
    }

    private void dealSelectorMonth() {
        firstTitle.setText(selectYear + "-" + selectMonth);
        setTextviewSize(selectMonth, mMonthAdapter);
        setMonth(selectMonth);
        initDays(Integer.parseInt(day));
        mDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
        wvDay.setVisibleItems(showItemNum);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(0);

        day = mDataDealUtil.dealDiffDays(currentYear, month);
        selectDay = "";
    }


    /**
     * 日 处理
     */
    private void dealDayADay2() {

        wvDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectDay = (String) mDaydapter.getItemText(wheel.getCurrentItem());

                setTextviewSize(selectDay, mDaydapter);
                firstTitle.setText(selectYear + "-" + selectMonth + "-" + selectDay);
            }
        });
    }

    /**
     * 初始化 年份集合
     */
    public void initYears() {
        arry_years.clear();
        for (int i = Integer.parseInt(mDataDealUtil.getYear()); i > 1971; i--) {
            arry_years.add(i);
        }
    }

    /**
     * 初始化 月份集合
     */
    public void initMonths(int months) {
        arry_months.clear();
        for (int i = 1; i <= months; i++) {
            arry_months.add(i);
        }
    }

    /**
     * 初始化 日集合
     */
    public void initDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i);
        }
    }

    /**
     * 适配器
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<Integer> list;

        protected CalendarTextAdapter(Context context, ArrayList<Integer> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.singel_date_item_picker, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public void setBirthdayListener(OnBirthListener onBirthListener) {
        this.onBirthListener = onBirthListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnSure) {
            if (onBirthListener != null) {
                if (selectYear.equals("") || selectMonth.equals("") || selectDay.equals("")) {
                    Toast.makeText(context, "选择的 时间参数 不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_anim_show_translate_set));
                    showFour.postOnAnimationDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBirthListener.onClick(selectYear, selectMonth, selectDay);
                            Log.d("cy", "" + selectYear + "" + selectMonth + "" + selectDay);
                            dismiss();
                        }
                    }, 700);
                }
            }
        } else if (v == btnCancel) {
            showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_anim_show_translate_set));
            showFour.postOnAnimationDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, 700);
        }
    }

    public interface OnBirthListener {
        void onClick(String year, String month, String day);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText 当前item字体大小
     * @param adapter         适配器
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    /**
     * 在调用类中，手动设置： 年 月 日
     *
     * @param year1  年
     * @param month1 月
     * @param day1   日
     */
    public void setDate(String year1, String month1, String day1, String year2, String month2, String day2) {

        firstTitle.setText(year1 + "-" + month1 + "-" + day1);
        /*lastTitle.setText(year2 + "-" + month2 + "-" + day2);*/

        Log.e(TAG, "setDate year1 : " + year1 + " ，month1 ： " + month1 + ", day1 : " + day1 + " , year2 : " + year2 + " ，month2 ： " + month2 + ", day2 : " + day2);

        selectYear = year1;
        selectMonth = month1;
        selectDay = day1;

        currentYear = year1;
        currentMonth = month1;
        currentDay = day1;

        if (currentYear.equals(mDataDealUtil.getYear())) {
            month = mDataDealUtil.getMonth();
        } else {
            month = 12 + "";
        }
        day = mDataDealUtil.dealDiffDays(currentYear, month);

        issetdata = true;
    }

    /**
     * 设置年份
     *
     * @param year 年 : 如果是当前年份，则显示当前月份；如果不是，则显示12个月份。
     */
    public int setYear(String year) {
        int yearIndex = 0;

        if (!year.equals(mDataDealUtil.getYear())) {
            this.month = 12 + "";
        } else {
            this.month = mDataDealUtil.getMonth();
        }

        for (int i = Integer.parseInt(mDataDealUtil.getYear()); i > 1950; i--) {
            if (i == Integer.parseInt(year)) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param month 月
     * @return
     */
    public int setMonth(String month) {
        int monthIndex = 0;
        day = mDataDealUtil.dealDiffDays(currentYear, month);
        int nowMonth = Integer.parseInt(month);
        for (int i = 1; i <= 12; i++) {
            if (nowMonth == i) {
                Log.e(TAG, "setMonth new : " + monthIndex);
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

}