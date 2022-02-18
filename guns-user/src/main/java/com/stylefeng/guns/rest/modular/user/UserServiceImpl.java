package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.dto.UserModel;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.vo.UserInfoModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username,String password) {

        String encryptPassword = MD5Util.encrypt(password);

        MoocUserT condition = new MoocUserT();
        condition.setUserName(username);

        MoocUserT moocUserT = moocUserTMapper.selectOne(condition);

        if(null != moocUserT && moocUserT.getUuid() > 0) {
            if(moocUserT.getUserPwd().equals(encryptPassword))
                return moocUserT.getUuid();
        }


        return moocUserT.getUuid();

    }

    @Override
    public boolean register(UserModel userModel) {
        MoocUserT moocUserT = new MoocUserT();

        moocUserT.setUserName(userModel.getUsername());
        String encryptPassword = MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(encryptPassword);

        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setBeginTime(new Date());
        moocUserT.setUpdateTime(new Date());

        Integer isSuccess = moocUserTMapper.insert(moocUserT);

        if(isSuccess > 0)
            return true;
        else
            return false;
    }

    /**
     * 检测用户名是否存在
     * @param username 需要检测的用户名
     * @return false：已存在，true：不存在
     */
    @Override
    public boolean checkUsername(String username) {

        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);

        Integer integer = moocUserTMapper.selectCount(entityWrapper);

        if(integer != null && integer > 0)
            return false;
        return true;

    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {

        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);

        return do2UserInfo(moocUserT);

    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        MoocUserT moocUserT = new MoocUserT();

        moocUserT.setUuid(Integer.parseInt(userInfoModel.getUuid()));
        moocUserT.setUserName(userInfoModel.getUsername());
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setLifeState(userInfoModel.getLifeState());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBeginTime(userInfoModel.getCreateTime());
        moocUserT.setUpdateTime(userInfoModel.getUpdateTime());

        moocUserTMapper.updateById(moocUserT);

        return userInfoModel;



    }

    private UserInfoModel do2UserInfo(MoocUserT moocUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(moocUserT.getUuid() + "");
        userInfoModel.setUsername(moocUserT.getUserName());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setLifeState(moocUserT.getLifeState());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setCreateTime(moocUserT.getBeginTime());
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime());

        return userInfoModel;

    }
}
