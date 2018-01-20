package com.yannischeng.baselib.base;

/**
 * 事件通知：BaseEvents 与 EventBusType 搭配使用
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class BaseEvents {

    private Object data;
    private int type;

    public BaseEvents(int type) {
        this.type = type;
    }

    public BaseEvents(Object data, int type) {
        this.data = data;
        this.type = type;
    }

    public Object getEBData() {
        return data;
    }

    public void setEBData(Object data) {
        this.data = data;
    }

    public int getEBType() {
        return type;
    }

    public void setEBType(int type) {
        this.type = type;
    }

}
