package com.ssm.model;

import com.google.common.base.MoreObjects;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:44
 */

public class UserModel {

    private Long id;
    private String userName;
    private String passWord;
    private String mail;
    private String phone;
    private Integer sex;
    private Integer auth;
    private String headImage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userName", userName)
                .add("passWord", passWord)
                .add("mail", mail)
                .add("phone", phone)
                .add("sex", sex)
                .add("auth", auth)
                .add("headImage", headImage)
                .add("loginDate", loginDate)
                .add("addTime", addTime)
                .add("status", status)
                .add("beginDate", beginDate)
                .add("endDate", endDate)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public UserModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public UserModel setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public UserModel setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getSex() {
        return sex;
    }

    public UserModel setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public Integer getAuth() {
        return auth;
    }

    public UserModel setAuth(Integer auth) {
        this.auth = auth;
        return this;
    }

    public String getHeadImage() {
        return headImage;
    }

    public UserModel setHeadImage(String headImage) {
        this.headImage = headImage;
        return this;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public UserModel setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public Date getAddTime() {
        return addTime;
    }

    public UserModel setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public UserModel setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public UserModel setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public UserModel setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
}
