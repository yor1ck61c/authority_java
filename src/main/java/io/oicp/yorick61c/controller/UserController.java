package io.oicp.yorick61c.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.oicp.yorick61c.domain.Token;
import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.service.UserService;
import io.oicp.yorick61c.utils.JsonUtil;
import io.oicp.yorick61c.utils.JwtUtil;
import jdk.nashorn.internal.parser.JSONParser;
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
            String token = JwtUtil.generateToken(user.getUsername());
            Token returnToken = new Token();
            returnToken.setToken(token);
            returnToken.setCode(200);
            return JsonUtil.obj2String(returnToken);
        }
    }

    @PostMapping("/getInfo")
    @ResponseBody
    public String getUserInfo(@CookieValue("Token") String token) throws JsonProcessingException {
        //根据token获取user对象全部信息
        User user = userService.findUserByUsername(JwtUtil.parse(token).getSubject());
        //将对象信息转为json字符串返回
        return JsonUtil.obj2String(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout(){
        return "success";
    }
}
