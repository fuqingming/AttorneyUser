package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.VipBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseVipBean {
    private ArrayList<VipBean> lists;

    public ArrayList<VipBean> getLists() {
        return lists;
    }

    public void setLists(ArrayList<VipBean> lists) {
        this.lists = lists;
    }
}
