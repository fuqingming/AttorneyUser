package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vip on 2018/5/2.
 */

public class LawyerBean implements Serializable {
    private String id;
    private String username;
    private String address;
    private String hx_user;
    private String realname;
    private String shopId;
    private String office;
    private String headimg;
    private String grade;
    private String levelIcon;
    private String work_year;
    private String shopName;
    private List<ProfessionBean> profession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHx_user() {
        return hx_user;
    }

    public void setHx_user(String hx_user) {
        this.hx_user = hx_user;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public String getWork_year() {
        return work_year;
    }

    public void setWork_year(String work_year) {
        this.work_year = work_year;
    }

    public List<ProfessionBean> getProfession() {
        return profession;
    }

    public void setProfession(List<ProfessionBean> profession) {
        this.profession = profession;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
