package com.xinfu.attorneyuser.bean.response;

import com.xinfu.attorneyuser.bean.base.MyOrderBean;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseMyOrderBean {
    private List<MyOrderBean> data;

    public List<MyOrderBean> getData() {
        return data;
    }

    public void setData(List<MyOrderBean> data) {
        this.data = data;
    }
}
