package com.xinfu.attorneyuser.bean.base;

import java.io.Serializable;

/**
 * Created by vip on 2018/5/2.
 */

public class UserBean implements Serializable
{
    private String id;          //用户id
    private String name;        //真实姓名
    private String phone;       //用户手机
    private String pass;        //密码
    private String sex;         //性别(1:男，0:女)
    private String age;         //年龄
    private String occupation;  //职业
    private String address;     //地址（上海市@浦东新区）
    private String integral;    //积分
    private String create_time; //注册时间
    private String status;      //用户状态(1:有效，0:无效)
    private String login_time;  //最后登录时间
    private String openId;
    private String type;        //注册来源（1:手机，2:微信，3:QQ，4:新浪）
    private String accessToken;
    private String wallect;     //用户余额（律币）
    private String emil;        //邮箱
    private String head;        //头像
    private String member_id;   //会员等级
    private String overall_lawyer;//充值律币总数
    private String frequency;   //登录次数
    private String hx_user;     //环信账号
    private String hx_pass;     //环信密码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getWallect() {
        return wallect;
    }

    public void setWallect(String wallect) {
        this.wallect = wallect;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getOverall_lawyer() {
        return overall_lawyer;
    }

    public void setOverall_lawyer(String overall_lawyer) {
        this.overall_lawyer = overall_lawyer;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getHx_user() {
        return hx_user;
    }

    public void setHx_user(String hx_user) {
        this.hx_user = hx_user;
    }

    public String getHx_pass() {
        return hx_pass;
    }

    public void setHx_pass(String hx_pass) {
        this.hx_pass = hx_pass;
    }
}
