package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.CityBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseCityBean {
    private ArrayList<CityBean> data;

    public ArrayList<CityBean> getData() {
        return data;
    }

    public void setData(ArrayList<CityBean> data) {
        this.data = data;
    }
}
