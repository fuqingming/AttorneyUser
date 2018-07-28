package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.VadioBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseVadioBean {
    private ArrayList<VadioBean> data;

    public ArrayList<VadioBean> getData() {
        return data;
    }

    public void setData(ArrayList<VadioBean> data) {
        this.data = data;
    }
}
