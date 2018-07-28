package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class LoginBean implements Serializable {
    private String token;
    private String user_id;
    private String phone;
    private String hx_user;
    private String hx_pass;
    private double wallect;
    private String head;
    private String isphone;
    private UserBean user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHx_user() {
        return hx_user;
    }

    public void setHx_user(String hx_user) {
        this.hx_user = hx_user;
    }

    public String getHx_pass() {
        return hx_pass;
    }

    public void setHx_pass(String hx_pass) {
        this.hx_pass = hx_pass;
    }

    public double getWallect() {
        return wallect;
    }

    public void setWallect(double wallect) {
        this.wallect = wallect;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getIsphone() {
        return isphone;
    }

    public void setIsphone(String isphone) {
        this.isphone = isphone;
    }
}
