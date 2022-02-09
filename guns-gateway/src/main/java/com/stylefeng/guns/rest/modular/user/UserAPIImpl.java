package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserAPIImpl implements UserAPI {

    @Override
    @PostMapping("/login")
    public boolean login(@RequestBody String username,@RequestBody String password) {
        return true;
    }
}
