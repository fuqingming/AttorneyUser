package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class VipCardActivationBean implements Serializable {
    private String cardNum;
    private String cardPwd;

    public VipCardActivationBean(String cardNum, String cardPwd) {
        this.cardNum = cardNum;
        this.cardPwd = cardPwd;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
}
