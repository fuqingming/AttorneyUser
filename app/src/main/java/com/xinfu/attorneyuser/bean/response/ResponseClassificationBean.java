package com.xinfu.attorneyuser.bean.response;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseClassificationBean {
    private List<ResponsePriceBean> data;

    public List<ResponsePriceBean> getData() {
        return data;
    }

    public void setData(List<ResponsePriceBean> data) {
        this.data = data;
    }
}
