package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.RefereeBean;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseRefereeBean {

    private List<RefereeBean> data;

    public List<RefereeBean> getData() {
        return data;
    }

    public void setData(List<RefereeBean> data) {
        this.data = data;
    }
}
