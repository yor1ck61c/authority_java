package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.service.UserService;
import io.oicp.yorick61c.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user){
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (userByUsername == null) {
            //用户不存在
            return null;
        } else {
            if (!userByUsername.getPassword().equals(user.getPassword())){
                //密码不正确
                return null;
            }
            //登录认证成功，生成JwtToken并返回。

            return JwtUtil.generateToken(user.getUsername());
        }

    }
}
