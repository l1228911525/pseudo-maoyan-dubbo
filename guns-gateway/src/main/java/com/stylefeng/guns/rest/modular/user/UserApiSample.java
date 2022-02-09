package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UserApiSample {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    public boolean login(String username,String password) {

        System.out.println("username = " + username + ", password = " + password);

        return userAPI.login(username, password);
    }
}
