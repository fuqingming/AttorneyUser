package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.LawyerBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseFindLawBean {
    private ArrayList<LawyerBean> data;

    public ArrayList<LawyerBean> getData() {
        return data;
    }

    public void setData(ArrayList<LawyerBean> data) {
        this.data = data;
    }
}
