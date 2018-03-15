package com.yannischeng.police.widget.date_picker.picker;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yannischeng.police.utils.DataDealUtil;
import com.yannischeng.police.widget.date_picker.adapter.AbstractWheelTextAdapter1;
import com.yannischeng.police.widget.date_picker.config.OnWheelScrollListener;
import com.yannischeng.police.widget.date_picker.config.WheelView;
import com.yannischeng.guolvtj.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

/**
 * DoublePicker  双日期选择器
 *
 * @author  wenjia.Cheng  cwj1714@163.com
 * @date    2017/10/10
*/
public class DoublePicker extends PopupWindow implements View.OnClickListener {

    private static final String TAG = "DoublePicker";

    private Context context;

    /**
     * 共2个日期选择器，定义6个自定义控件 选择器1
     */
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;

    /**
     * 共2个日期选择器，定义6个自定义控件 选择器2
     */
    private WheelView wvYear2;
    private WheelView wvMonth2;
    private WheelView wvDay2;

    private TextView btnSure;
    private TextView btnCancel;
    private TextView btnshowFour;
    private TextView firstTitle;
    private TextView lastTitle;
    private RadioGroup mRadioGroup;
    private RadioButton dayBtn, weekBtn, monthBtn, yearBtn;
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
     * 获取当前日期 选择器1
     */
    private String currentYear;
    private String currentMonth;
    private String currentDay;

    /**
     * 获取当前日期 选择器2
     */
    private String currentYear2;
    private String currentMonth2;
    private String currentDay2;

    /**
     * 设置字号大小
     */
    private int maxTextSize = 20;
    private int minTextSize = 14;

    /**
     * 是否初始化日期
     */
    private boolean issetdata = false;
    private boolean isShowFour = true;

    /**
     * 获取选择的日期 选择器1
     */
    private String selectYear;
    private String selectMonth;
    private String selectDay;

    /**
     * 获取选择的日期 选择器2
     */
    private String selectYear2;
    private String selectMonth2;
    private String selectDay2;

    /**
     * 显示日期的个数
     */
    private int showItemNum = 8;

    private OnBirthListener onBirthListener;
    private DataDealUtil mDataDealUtil;

    /**
     * 构造函数
     */
    public DoublePicker(final Context context) {
        super(context);
        this.context = context;
        mDataDealUtil = new DataDealUtil();

        currentDay = mDataDealUtil.getDay();
        currentDay2 = mDataDealUtil.getDay();

        currentMonth = mDataDealUtil.getMonth();
        currentMonth2 = mDataDealUtil.getMonth();

        currentYear = mDataDealUtil.getYear();
        currentYear2 = mDataDealUtil.getYear();

        //界面控件初始化
        View view = View.inflate(context, R.layout.dialog_myinfo_changebirth, null);
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

        wvYear2 = (WheelView) view.findViewById(R.id.wv_birth_year2);
        wvMonth2 = (WheelView) view.findViewById(R.id.wv_birth_month2);
        wvDay2 = (WheelView) view.findViewById(R.id.wv_birth_day2);

        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);
        btnshowFour = (TextView) view.findViewById(R.id.change_btn);
        firstTitle = (TextView) view.findViewById(R.id.first_time);
        lastTitle = (TextView) view.findViewById(R.id.last_time);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.four_time_choose);
        dayBtn = (RadioButton) view.findViewById(R.id.day_bottom);
        weekBtn = (RadioButton) view.findViewById(R.id.week_bottom);
        monthBtn = (RadioButton) view.findViewById(R.id.month_bottom);
        yearBtn = (RadioButton) view.findViewById(R.id.year_bottom);
        showFour = (AutoLinearLayout) view.findViewById(R.id.ly_myinfo_changeaddress);

        lineView = view.findViewById(R.id.choose_time_line);
        mLayoutShowTime = (AutoLinearLayout) view.findViewById(R.id.choose_time_layout);


        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnshowFour.setOnClickListener(this);

        setFourText();
        dealPickerView();
    }


    //控制双时间选择器 控件 的显隐藏
    private void dealPickerView() {

        btnshowFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnshowFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_rotate));
                if (isShowFour) {
                    Log.e(TAG, "dealPickerView true ");
                    btnshowFour.setBackgroundResource(R.drawable.ic_dashboard_origon_24dp);
                    btnSure.setBackgroundResource(R.drawable.ic_done_origion_24dp);
                    btnCancel.setBackgroundResource(R.drawable.ic_clear_origion_24dp);
                    //隐藏 4个基本时间段选择,显示 具体时间段选择
                    mRadioGroup.setVisibility(View.GONE);
                    firstTitle.setVisibility(View.VISIBLE);
                    lastTitle.setVisibility(View.VISIBLE);
                    lineView.setVisibility(View.VISIBLE);
                    mLayoutShowTime.setVisibility(View.VISIBLE);
                    mLayoutShowTime.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_anim_show_bottom));
                    btnCancel.setVisibility(View.VISIBLE);
                    btnSure.setVisibility(View.VISIBLE);
                    btnshowFour.setVisibility(View.VISIBLE);
                    isShowFour = false;
                } else {
                    Log.e(TAG, "dealPickerView false ");
                    btnshowFour.setBackgroundResource(R.drawable.choose_data_change);
                    btnSure.setBackgroundResource(R.drawable.choose_data_ok);
                    btnCancel.setBackgroundResource(R.drawable.choose_data_no);
                    //显示 4个基本时间段选择,隐藏 具体时间段选择
                    mRadioGroup.setVisibility(View.VISIBLE);
                    firstTitle.setVisibility(View.GONE);
                    lastTitle.setVisibility(View.GONE);
                    lineView.setVisibility(View.GONE);

                    //mLayoutShowTime.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_set));
                    mLayoutShowTime.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.VISIBLE);
                    btnSure.setVisibility(View.GONE);
                    btnshowFour.setVisibility(View.VISIBLE);
                    isShowFour = true;
                }
            }
        });

    }

    /**
     * 为4个时间填写文本
     */
    public void setFourText() {

        onFourTimeClick();
    }

    private void onFourTimeClick() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.day_bottom) {
                    if (onBirthListener != null) {
                        showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_translate_set_line_out));
                        showFour.postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBirthListener.onChooseFourTime(0);
                                dismiss();
                            }
                        }, 700);
                    }
                } else if (checkedId == R.id.week_bottom) {
                    if (onBirthListener != null) {
                        showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_translate_set_line_out));
                        showFour.postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBirthListener.onChooseFourTime(1);
                                dismiss();
                            }
                        }, 700);
                    }
                } else if (checkedId == R.id.month_bottom) {
                    if (onBirthListener != null) {
                        showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_translate_set_line_out));
                        showFour.postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBirthListener.onChooseFourTime(2);
                                dismiss();
                            }
                        }, 700);
                    }
                }else if (checkedId == R.id.year_bottom){
                    if (onBirthListener != null) {
                        showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_translate_set_line_out));
                        showFour.postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBirthListener.onChooseFourTime(3);
                                dismiss();
                            }
                        }, 700);
                    }
                }
            }

        });
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

        wvYear2.setVisibleItems(showItemNum);
        wvYear2.setViewAdapter(mYearAdapter);
        wvYear2.setCurrentItem(setYear(currentYear2));

        initMonths(Integer.parseInt(month));
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentMonth), maxTextSize, minTextSize);
        wvMonth.setVisibleItems(showItemNum);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));

        wvMonth2.setVisibleItems(showItemNum);
        wvMonth2.setViewAdapter(mMonthAdapter);
        wvMonth2.setCurrentItem(setMonth(currentMonth2));

        initDays(Integer.parseInt(day));
        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(currentDay) - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(showItemNum);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(Integer.parseInt(currentDay) - 1);

        wvDay2.setVisibleItems(showItemNum);
        wvDay2.setViewAdapter(mDaydapter);
        wvDay2.setCurrentItem(Integer.parseInt(currentDay2) - 1);
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

                if (Integer.parseInt(selectYear) > Integer.parseInt(selectYear2)) {
                    Toast.makeText(context, "请注意 年份 的选择！", Toast.LENGTH_SHORT).show();
                    //selectYear = "";
                    firstTitle.setText("");
                } else {
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

            }
        });

        wvYear2.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectYear2 = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                //判断选择时间段的合理性
                if (Integer.parseInt(selectYear) > Integer.parseInt(selectYear2)) {
                    Toast.makeText(context, "请注意 年份 的选择！", Toast.LENGTH_SHORT).show();
                    //selectYear2 = "";
                    lastTitle.setText("");
                } else {
                    lastTitle.setText(selectYear2);
                    setTextviewSize(selectYear2, mYearAdapter);
                    currentYear2 = selectYear2;
                    Log.d("currentYear==", currentYear2);
                    setYear(currentYear2);
                    initMonths(Integer.parseInt(month));
                    mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                    wvMonth2.setVisibleItems(showItemNum);
                    wvMonth2.setViewAdapter(mMonthAdapter);
                    wvMonth2.setCurrentItem(0);

                    day = mDataDealUtil.dealDiffDays(currentYear2, month);

                    selectMonth2 = "";
                    selectDay2 = "";
                }

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

                //年份相同
                if (Integer.parseInt(selectYear) == Integer.parseInt(selectYear2)) {
                    //前者月份 > 后者月份  异常
                    if (Integer.parseInt(selectMonth) > Integer.parseInt(selectMonth2)) {
                        Toast.makeText(context, "请注意 月份 的选择！", Toast.LENGTH_SHORT).show();
                        //selectYear2 = "";
                        firstTitle.setText(selectYear);
                    } else {
                        dealSelectorMonth();
                    }
                } else if (Integer.parseInt(selectYear) < Integer.parseInt(selectYear2)) {
                    dealSelectorMonth();
                }

            }
        });

        wvMonth2.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectMonth2 = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                if (Integer.parseInt(selectYear) == Integer.parseInt(selectYear2)) {
                    if (Integer.parseInt(selectMonth) > Integer.parseInt(selectMonth2)) {
                        Toast.makeText(context, "请注意 月份 的选择！", Toast.LENGTH_SHORT).show();
                        //selectYear2 = "";
                    } else {
                        dealSelectorMonth2();
                    }
                } else if (Integer.parseInt(selectYear) < Integer.parseInt(selectYear2)) {
                    dealSelectorMonth2();
                }
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

    private void dealSelectorMonth2() {
        lastTitle.setText(selectYear2 + "-" + selectMonth2);
        setTextviewSize(selectMonth2, mMonthAdapter);
        setMonth(selectMonth2);
        initDays(Integer.parseInt(day));
        mDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
        wvDay2.setVisibleItems(showItemNum);
        wvDay2.setViewAdapter(mDaydapter);
        wvDay2.setCurrentItem(0);

        day = mDataDealUtil.dealDiffDays(currentYear2, month);
        selectDay2 = "";
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

                if (Integer.parseInt(selectYear) == Integer.parseInt(selectYear2)) {
                    //月份相同
                    if (Integer.parseInt(selectMonth) == Integer.parseInt(selectMonth2)) {
                        //判断日期
                        if (Integer.parseInt(selectDay) > Integer.parseInt(selectDay2)) {
                            Toast.makeText(context, "请注意 日期 的选择！", Toast.LENGTH_SHORT).show();
                            //selectDay2 = "";
                            firstTitle.setText(selectYear + "-" + selectMonth);
                        } else {
                            setTextviewSize(selectDay, mDaydapter);
                            firstTitle.setText(selectYear + "-" + selectMonth + "-" + selectDay);
                        }
                    } else if (Integer.parseInt(selectMonth) < Integer.parseInt(selectMonth2)) {
                        //月份不同
                        setTextviewSize(selectDay, mDaydapter);
                        firstTitle.setText(selectYear + "-" + selectMonth + "-" + selectDay);
                    }
                } else if (Integer.parseInt(selectYear) < Integer.parseInt(selectYear2)) {
                    //年份不同
                    setTextviewSize(selectDay, mDaydapter);
                    firstTitle.setText(selectYear + "-" + selectMonth + "-" + selectDay);
                }
            }
        });

        wvDay2.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                selectDay2 = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                //年份相同
                if (Integer.parseInt(selectYear) == Integer.parseInt(selectYear2)) {
                    //月份相同
                    if (Integer.parseInt(selectMonth) == Integer.parseInt(selectMonth2)) {
                        //判断日期
                        if (Integer.parseInt(selectDay) > Integer.parseInt(selectDay2)) {
                            Toast.makeText(context, "请注意 日期 的选择！", Toast.LENGTH_SHORT).show();
                            //selectDay2 = "";
                            lastTitle.setText(selectYear2 + "-" + selectMonth2);
                        } else {
                            setTextviewSize(selectDay2, mDaydapter);
                            lastTitle.setText(selectYear2 + "-" + selectMonth2 + "-" + selectDay2);
                        }
                    } else if (Integer.parseInt(selectMonth) < Integer.parseInt(selectMonth2)) {
                        //月份不同
                        setTextviewSize(selectDay2, mDaydapter);
                        lastTitle.setText(selectYear2 + "-" + selectMonth2 + "-" + selectDay2);
                    }
                } else if (Integer.parseInt(selectYear) < Integer.parseInt(selectYear2)) {
                    //年份不同
                    setTextviewSize(selectDay2, mDaydapter);
                    lastTitle.setText(selectYear2 + "-" + selectMonth2 + "-" + selectDay2);
                }
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
                if (selectYear.equals("") || selectMonth.equals("") || selectDay.equals("") || selectYear2.equals("") || selectMonth2.equals("") || selectDay2.equals("")) {
                    Toast.makeText(context, "选择的 时间参数 不能为空！", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(selectYear) > Integer.parseInt(selectYear2) || Integer.parseInt(selectMonth) > Integer.parseInt(selectMonth2) || Integer.parseInt(selectDay) > Integer.parseInt(selectDay2)) {
                    Toast.makeText(context, "注意选择 时间 的合理性！", Toast.LENGTH_SHORT).show();
                } else {
                    showFour.startAnimation(AnimationUtils.loadAnimation(context, R.anim.date_picker_anim_show_translate_set));
                    showFour.postOnAnimationDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBirthListener.onClick(selectYear, selectMonth, selectDay, selectYear2, selectMonth2, selectDay2);
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
            //dismiss();
        }
    }

    public interface OnBirthListener {
        void onClick(String year, String month, String day, String year2, String month2, String day2);

        void onChooseFourTime(int dataTime);
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
        lastTitle.setText(year2 + "-" + month2 + "-" + day2);

        Log.e(TAG, "setDate year1 : " + year1 + " ，month1 ： " + month1 + ", day1 : " + day1 + " , year2 : " + year2 + " ，month2 ： " + month2 + ", day2 : " + day2);

        selectYear = year1;
        selectMonth = month1;
        selectDay = day1;
        selectYear2 = year2;
        selectMonth2 = month2;
        selectDay2 = day2;

        currentYear = year1;
        currentMonth = month1;
        currentDay = day1;
        currentYear2 = year2;
        currentMonth2 = month2;
        currentDay2 = day2;

        if (currentYear2.equals(mDataDealUtil.getYear())) {
            month = mDataDealUtil.getMonth();
        } else {
            month = 12 + "";
        }
        day = mDataDealUtil.dealDiffDays(currentYear2, month);

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
        day = mDataDealUtil.dealDiffDays(currentYear2, month);
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