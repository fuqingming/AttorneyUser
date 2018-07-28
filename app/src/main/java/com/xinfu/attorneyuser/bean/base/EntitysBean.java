package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class EntitysBean implements Serializable {
    private String id;
    private String image;
    private String title;
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
