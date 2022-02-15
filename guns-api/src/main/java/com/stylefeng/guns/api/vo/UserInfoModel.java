package com.stylefeng.guns.api.vo;

import java.util.Date;

public class UserInfoModel {

    private String uuid;
    // 用户名
    private String username;
    // 昵称
    private String nickname;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 性别
    private int sex;
    // 生日
    private Date birthday;
    // [0-单身，1-热恋中，2-已婚，3-为人父母]
    private int lifeState;
    // 个人简介
    private String biography;
    // 地址
    private String address;
    // 头像地址
    private String headAddress;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

    public UserInfoModel() {
    }

    public UserInfoModel(String uuid, String username, String nickname, String email, String phone, int sex, Date birthday, int lifeState, String biography, String address, String headAddress, Date createTime, Date updateTime) {
        this.uuid = uuid;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.lifeState = lifeState;
        this.biography = biography;
        this.address = address;
        this.headAddress = headAddress;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getLifeState() {
        return lifeState;
    }

    public void setLifeState(int lifeState) {
        this.lifeState = lifeState;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadAddress() {
        return headAddress;
    }

    public void setHeadAddress(String headAddress) {
        this.headAddress = headAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
