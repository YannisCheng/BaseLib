package com.yannischeng.police.bean;

/**
 * Created by wenjia Cheng on 2017/9/11.
 * e-mail:cwj1714@163.com
 * <p>
 * 人员列表 内容
 */
public class PersonalListBean {

    private String personalName;
    private String personalContent;
    private String firstChar;
    private int indexFirstChar;

    public PersonalListBean() {
    }

    public PersonalListBean(String personalName, String personalContent) {
        this.personalName = personalName;
        this.personalContent = personalContent;
    }

    public int getIndexFirstChar() {
        return indexFirstChar;
    }

    public void setIndexFirstChar(int indexFirstChar) {
        this.indexFirstChar = indexFirstChar;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPersonalContent() {
        return personalContent;
    }

    public void setPersonalContent(String personalContent) {
        this.personalContent = personalContent;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    @Override
    public String toString() {
        return "PersonalListBean{" +
                "personalName='" + personalName + '\'' +
                ", personalContent='" + personalContent + '\'' +
                ", firstChar='" + firstChar + '\'' +
                ", indexFirstChar=" + indexFirstChar +
                '}';
    }
}
