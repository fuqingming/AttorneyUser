package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.EnterpriseMsgBean;

import java.util.ArrayList;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseEnterpriseMsgBean {
    private ArrayList<String> tags;
    private ArrayList<EnterpriseMsgBean> articles;

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<EnterpriseMsgBean> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<EnterpriseMsgBean> articles) {
        this.articles = articles;
    }
}
