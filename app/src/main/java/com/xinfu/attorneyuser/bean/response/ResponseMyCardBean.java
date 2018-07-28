package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.UserInfoBean;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseMyCardBean {

    private Common common;
    private Vip vip;

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }

    public class Common
    {
        private String id;
        private String head;
        private String nickname;
        private String realname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }

    public class Vip
    {
        private String title;
        private String summary;
        private String mainImage;
        private String bgimg;
        private String description;
        private String expireAt;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getBgimg() {
            return bgimg;
        }

        public void setBgimg(String bgimg) {
            this.bgimg = bgimg;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(String expireAt) {
            this.expireAt = expireAt;
        }
    }
}
