package com.yannischeng.police.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wenjia Cheng on 2017/10/10.
 * e-mail:cwj1714@163.com
 * <p>
 * 工具类 - 日期，事件
 */

public class DataDealUtil {

    private static final String TAG = "DataDealUtil";

    /**
     * 获取年
     */
    public String getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    /**
     * 获取月
     */
    public String getMonth() {
        //实际月份
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1 + "";
    }

    public String getMonth1() {
        //月份比实际月份少1天
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + "";
    }


    /**
     * 获取日
     */
    public String getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE) + "";
    }

    /**
     * 计算每月多少天 ：闰年+2月判断
     *
     * @param month 月份
     * @param year  年份
     */
    public String dealDiffDays(String year, String month) {
        Log.e(TAG, "dealDiffDays year : " + year + ", month is : " + month);
        String day = "";
        boolean leayyear = false;
        //是否为 闰年
        if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) || Integer.parseInt(year) % 400 == 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }

        //判断每月的具体天数
        for (int i = 1; i <= 12; i++) {
            switch (Integer.parseInt(month)) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31 + "";
                    break;
                case 2:
                    if (leayyear) {
                        day = 29 + "";
                    } else {
                        day = 28 + "";
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    day = 30 + "";
                    break;
            }
        }

        //视具体功能而定
        if (year.equals(getYear()) && month.equals(getMonth())) {
            day = getDay();
        }

        return day;
    }

    /**
     * 获取 当前系统时间，精确方式：mm
     */
    public String getCurrentTimeSystem(String dataFormatStr) {
        //int index = "GyMdkHmsSEDFwWahKzZLc".indexOf(format);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format1 = new SimpleDateFormat(dataFormatStr);
        //获取当前时间 -- 作为录音文件的名字
        return format1.format(new Date());
    }
}
