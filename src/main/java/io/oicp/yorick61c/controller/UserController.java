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
/*
* @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用
* 该注释声明这个是Controller类，类中的方法返回的都是字符串
*/
@RequestMapping("/user")
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    /*
    * @RequestMapping(method = RequestMethod.POST)的快捷方式
    * */
    @PostMapping("/login")
    public String login(@RequestBody User user){
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (userByUsername == null) {
            //用户不存在
            Token returnToken = new Token();
            returnToken.setCode(300);
            returnToken.setMsg("该用户不存在");
            return JsonUtil.obj2String(returnToken);
        } else {
            if (!userByUsername.getPassword().equals(user.getPassword())){
                //密码不正确
                Token returnToken = new Token();
                returnToken.setMsg("密码错误");
                return JsonUtil.obj2String(returnToken);
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
    // @RequestHeader注解主要是将请求头的信息区数据，映射到功能处理方法的参数上。@CookieValue注解主要是将请求的Cookie数据，映射到功能处理方法的参数上。
    public String getUserInfo(@CookieValue("Token") String token) throws JsonProcessingException {
        //根据token获取user对象全部信息
        User user = userService.findUserByUsername(JwtUtil.parse(token).getSubject());
        //将对象信息转为json字符串返回
        return JsonUtil.obj2String(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout(){

        System.out.println("success");
        return "success";
    }
}
