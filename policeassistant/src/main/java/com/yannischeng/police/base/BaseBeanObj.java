package com.yannischeng.police.base;

import java.io.Serializable;

/**
 * BaseBeanObj 返回结果：对象类
 *
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2017/11/28
 */
public class BaseBeanObj<T> implements Serializable {

    /**
     * message : success
     * code : 100
     * data : {"c_id":9,"company":"居家乐科技","c_code":"123123"}
     */

    private String message;
    private int code;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBeanObj{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
