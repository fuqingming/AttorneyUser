package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by vip on 2018/5/2.
 */

public class BalanceBean implements Serializable {
    private Common common;

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public class Common
    {
        private int balance;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
}
