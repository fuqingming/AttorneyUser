package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class VadioBean implements Serializable {
    private String label;
    private String slug;
    private List<EntitysBean> entitys;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<EntitysBean> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<EntitysBean> entitys) {
        this.entitys = entitys;
    }
}
