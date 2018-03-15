package com.yannischeng.police.base;

import java.io.Serializable;
import java.util.List;

/**
 * BaseBeanList 返回结果：集合基类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class BaseBeanList<T> implements Serializable {

    private List<T> data;
}
