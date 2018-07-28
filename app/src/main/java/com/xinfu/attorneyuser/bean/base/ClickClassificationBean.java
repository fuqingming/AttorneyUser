package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class ClickClassificationBean implements Serializable {
    private String name;
    private String num;
    private int position;
    private boolean isClick;

    public ClickClassificationBean(String name, String num, int position, boolean isClick) {
        this.name = name;
        this.num = num;
        this.position = position;
        this.isClick = isClick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
