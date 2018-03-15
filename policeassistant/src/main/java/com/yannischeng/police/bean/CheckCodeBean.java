package com.yannischeng.police.bean;

/**
 * @author wenjia.Cheng  cwj1714@163.com
 * @date 2018/3/13
 */
public class CheckCodeBean {

    /**
     * c_id : 9
     * company : 居家乐科技
     * c_code : 123123
     */

    private int c_id;
    private String company;
    private String c_code;

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public int getC_id() {
        return c_id;
    }

    public String getCompany() {
        return company;
    }

    public String getC_code() {
        return c_code;
    }

    @Override
    public String toString() {
        return "CheckCodeBean{" +
                "c_id=" + c_id +
                ", company='" + company + '\'' +
                ", c_code='" + c_code + '\'' +
                '}';
    }
}
