package com.xinfu.attorneyuser.bean.response;


import com.xinfu.attorneyuser.bean.base.LawyerCommonBean;
import com.xinfu.attorneyuser.bean.base.ProfessionBean;
import com.xinfu.attorneyuser.bean.base.RefereeBean;

import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class ResponseLawyerMainBean {

    private int is_follow;
    private String count;
    private String message_count;
    private Res res;
    private List<LawyerCommonBean> common;

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage_count() {
        return message_count;
    }

    public void setMessage_count(String message_count) {
        this.message_count = message_count;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public List<LawyerCommonBean> getCommon() {
        return common;
    }

    public void setCommon(List<LawyerCommonBean> common) {
        this.common = common;
    }

    public class Res{

        private List<ProfessionBean> profession;
        private String isPerson;
        private String focus;
        private String id;
        private String headimg;
        private String hx_user;
        private String username;
        private String shopId;
        private String office;
        private int grade;
        private String levelIcon;
        private String evaluate;
        private String work_year;

        public List<ProfessionBean> getProfession() {
            return profession;
        }

        public void setProfession(List<ProfessionBean> profession) {
            this.profession = profession;
        }

        public String getIsPerson() {
            return isPerson;
        }

        public void setIsPerson(String isPerson) {
            this.isPerson = isPerson;
        }

        public String getFocus() {
            return focus;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getHx_user() {
            return hx_user;
        }

        public void setHx_user(String hx_user) {
            this.hx_user = hx_user;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getLevelIcon() {
            return levelIcon;
        }

        public void setLevelIcon(String levelIcon) {
            this.levelIcon = levelIcon;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }

        public String getWork_year() {
            return work_year;
        }

        public void setWork_year(String work_year) {
            this.work_year = work_year;
        }
    }
}
