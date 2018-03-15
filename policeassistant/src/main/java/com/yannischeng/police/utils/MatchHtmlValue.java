package com.yannischeng.police.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MatchHtmlValue
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/12/27
 */
public class MatchHtmlValue {

    /**
     * 返回指定标签的结果
     *
     * @param source  源Html代码
     * @param element 名称
     * @param attr    标签的属性名称
     * @return 指定标签结果
     */
    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }
}
