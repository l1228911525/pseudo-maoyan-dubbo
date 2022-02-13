package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.dto.UserModel;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserAPIImpl implements UserAPI {

    @Override
    public int login(String username,String password) {
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        return true;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public void getUserInfo(String authorizationHeader) {

    }

    @Override
    public void updateUserInfo() {

    }
}
