package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

//@Component
@RestController
@RequestMapping("/user")
public class UserApiSample {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;
    @GetMapping("/login")
    public int login(String username,String password) {

        System.out.println("username = " + username + ", password = " + password);

        return userAPI.login(username, password);
    }
}
