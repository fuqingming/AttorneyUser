package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class CityBean implements Serializable {
    private String id;
    private String shortname;
    private List<ChildBean> child;

    public CityBean(String shortname) {
        this.shortname = shortname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }
}
