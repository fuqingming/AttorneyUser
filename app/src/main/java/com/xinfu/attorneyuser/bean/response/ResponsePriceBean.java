package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.PriceBean;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponsePriceBean {

    private String id;
    private String type;
    private List<PriceBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PriceBean> getChild() {
        return child;
    }

    public void setChild(List<PriceBean> child) {
        this.child = child;
    }
}
