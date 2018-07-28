package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.BannerBean;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseBannerBean{
    private List<BannerBean> data;

    public List<BannerBean> getData() {
        return data;
    }

    public void setData(List<BannerBean> data) {
        this.data = data;
    }
}
