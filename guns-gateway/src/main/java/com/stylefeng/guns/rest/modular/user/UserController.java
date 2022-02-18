package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.dto.UserModel;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.vo.ResponseVO;
import com.stylefeng.guns.api.vo.UserInfoModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    @PostMapping("/register")
    public ResponseVO register(UserModel userModel) {

        if(userModel.getUsername() == null || userModel.getUsername().length() == 0)
            return ResponseVO.serviceFail("用户名不能为空");

        if(userModel.getPassword() == null || userModel.getPassword().length() == 0)
            return ResponseVO.serviceFail("密码不能为空");

        boolean isSuccess = userAPI.register(userModel);

        if(isSuccess)
            return ResponseVO.success("注册成功");
        else
            return ResponseVO.serviceFail("注册失败");
    }

    @PostMapping("/check")
    public ResponseVO checkUsername(String username){

        if(username == null || "".equals(username))
            return ResponseVO.serviceFail("用户名不能为空");

        boolean isNoExist = userAPI.checkUsername(username);

        if(isNoExist) {
            return ResponseVO.success("用户名不存在");
        } else {
            return ResponseVO.serviceFail("用户名已存在");
        }

    }

    @GetMapping("/logout")
    public ResponseVO logout() {

        return ResponseVO.success("用户退出成功");

    }

    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo() {

        String currentId = CurrentUser.getCurrentId();

        if(currentId == null || "".equals(currentId))
            return ResponseVO.serviceFail("用户未登陆");

        UserInfoModel userInfo = userAPI.getUserInfo(Integer.parseInt(currentId));

        if(null == userInfo) {
            return ResponseVO.serviceFail("查询失败，用户不存在");
        }

        return ResponseVO.success(userInfo);

    }

    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel) {

        String currentId = CurrentUser.getCurrentId();

        if(currentId != null && !"".equals(currentId)){

            String userInfoUUID = userInfoModel.getUuid();

            if(!currentId.equals(userInfoUUID)) {
                return ResponseVO.serviceFail("请修改您的个人信息");
            }

            UserInfoModel userInfoModelResult = userAPI.updateUserInfo(userInfoModel);

            return ResponseVO.success(userInfoModelResult);
        } else {
            return ResponseVO.serviceFail("用户尚未登陆");
        }

    }

}
