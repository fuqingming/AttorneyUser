package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class LawyerCommonBean implements Serializable {
    private String lcon;
    private String howLongAgo;
    private String uid;
    private String uname;
    private String head;
    private String lid;

    public String getLcon() {
        return lcon;
    }

    public void setLcon(String lcon) {
        this.lcon = lcon;
    }

    public String getHowLongAgo() {
        return howLongAgo;
    }

    public void setHowLongAgo(String howLongAgo) {
        this.howLongAgo = howLongAgo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
}
