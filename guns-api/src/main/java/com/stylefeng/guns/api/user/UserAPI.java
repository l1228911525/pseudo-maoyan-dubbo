package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.dto.UserModel;
import com.stylefeng.guns.api.vo.UserInfoModel;

public interface UserAPI {

    /**
     * 用户登陆
     * @param username 用户名
     * @param password 密码
     * @return user id
     */
    public int login(String username, String password);

    public boolean register(UserModel userModel);

    public boolean checkUsername(String username);

    public boolean logout();

    public UserInfoModel getUserInfo(int uuid);

    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

}
