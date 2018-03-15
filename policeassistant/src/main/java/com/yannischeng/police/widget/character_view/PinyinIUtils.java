package com.yannischeng.police.widget.character_view;

import android.util.Log;

import com.yannischeng.police.bean.PersonalListBean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenjia Cheng on 2017/9/11.
 * e-mail:cwj1714@163.com
 * <p>
 * 使用 pinyin4j-2.5.0 jar包，处理 '汉字' 与 '拼音'
 */
public class PinyinIUtils {

    private static final String TAG = "PinyinIUtils";
    private static HanyuPinyinOutputFormat format = null;

    //设置format
    private static void setPinyinSettings() {
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    //获取名字的拼音String串
    public static List<String> getPinyin(List<PersonalListBean> mList) {

        /**
         * mList -> String item-> char[] -> String 拼音 -> String的拼音list集合
         */
        List<String> stringList = new ArrayList<String>();

        setPinyinSettings();

        //遍历每一个名字
        for (PersonalListBean item : mList) {
            String outStr = "";
            //将名字生成为char数组
            char[] input = item.getPersonalName().trim().toCharArray();

            //遍历char数组
            for (char itemC : input) {
                //showLog
                //Log.e(TAG, "getPinyin: " + itemC);

                //将char转换为Character
                if (Character.toString(itemC).matches("[\\u4E00-\\u9FA5]+")) {
                    //接收A-Z字符 组成的拼音字符串
                    String[] temp = new String[0];
                    try {
                        temp = PinyinHelper.toHanyuPinyinStringArray(itemC, format);
                        //Log.e(TAG, "getPinyin temp : " + temp );
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                    outStr += temp[0];
                    //Log.e(TAG, "getPinyin outStr: " + outStr);
                } else {
                    outStr += Character.toString(itemC);
                    //Log.e(TAG, "getPinyin outStr: " + outStr);
                }
            }

            //将各个名字的拼音字符串添加至String集合中
            //Log.e(TAG, "getPinyin  end outStr :" + outStr);
            stringList.add(outStr);
        }
        return stringList;

    }

    //获取汉字转化为拼音之后的首字母
    public static List<String> getPinyinFirst(List<String> mList) {
        //StringBuffer stringBuffer = new StringBuffer();
        List<String> mLlist = new ArrayList<String>();

        //遍历含有String类型的名字拼音
        for (String item : mList) {
            char[] chars = item.toCharArray();

            setPinyinSettings();

            //仅遍历姓氏
            if (chars[0] > 128) {

                //log:E/PinyinIUtils: getPinyinFirst > 128: 钱
                Log.e(TAG, "getPinyinFirst > 128: " + chars[0]);
                try {
                    String[] mStrs = PinyinHelper.toHanyuPinyinStringArray(chars[0], format);
                    if (mStrs != null) {
                        mLlist.add(String.valueOf(mStrs[0].charAt(0)));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                //Log.e(TAG, "getPinyinFirst: " + chars[0]);
                mLlist.add(String.valueOf(chars[0]));
            }
        }

        //log:E/PinyinIUtils: getPinyinFirst: zqslzwzw
        //Log.e(TAG, "getPinyinFirst: " + stringBuffer.toString().replace("\\W", "").trim());

        return /*stringBuffer.toString().replace("\\W", "").trim()*/ mLlist;
    }

    //仅仅获取 汉字首字母
    public static List<String> getPinyinFirstChar(List<PersonalListBean> mList) {
        //StringBuffer stringBuffer = new StringBuffer();
        List<String> mLlist = new ArrayList<String>();

        //遍历含有String类型的名字拼音
        for (PersonalListBean item : mList) {
            char[] chars = item.getPersonalName().toCharArray();

            setPinyinSettings();

            //仅遍历姓氏
            if (chars[0] > 128) {

                //log:E/PinyinIUtils: getPinyinFirst > 128: 钱
                //Log.e(TAG, "getPinyinFirst > 128: " + chars[0]);
                try {
                    String[] mStrs = PinyinHelper.toHanyuPinyinStringArray(chars[0], format);
                    if (mStrs != null) {
                        mLlist.add(String.valueOf(mStrs[0].charAt(0)));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                //Log.e(TAG, "getPinyinFirst: " + chars[0]);
                mLlist.add(String.valueOf(chars[0]));
            }
        }

        //log:E/PinyinIUtils: getPinyinFirst: zqslzwzw
        //Log.e(TAG, "getPinyinFirst: " + stringBuffer.toString().replace("\\W", "").trim());

        return /*stringBuffer.toString().replace("\\W", "").trim()*/ mLlist;
    }


}
