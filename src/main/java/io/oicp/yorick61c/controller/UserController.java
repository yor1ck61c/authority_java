package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.domain.login.Token;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.service.PrivilegeService;
import io.oicp.yorick61c.service.UserService;
import io.oicp.yorick61c.utils.JsonUtil;
import io.oicp.yorick61c.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
/*
* @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用
* 该注释声明这个是Controller类，类中的方法返回的都是字符串
*/
@RequestMapping("/user")
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    @Resource(name = "PrivilegeService")
    private PrivilegeService privilegeService;

    /*
    * @RequestMapping(method = RequestMethod.POST)的快捷方式
    * */
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session){
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
            session.setAttribute("username", user.getUsername());
            Token returnToken = new Token();
            returnToken.setToken(token);
            returnToken.setCode(200);
            return JsonUtil.obj2String(returnToken);
        }
    }

    @PostMapping("/getInfo")
    @ResponseBody
    // @RequestHeader注解主要是将请求头的信息区数据，映射到功能处理方法的参数上。@CookieValue注解主要是将请求的Cookie数据，映射到功能处理方法的参数上。
    public String getUserInfo(@CookieValue("Token") String token) {
        //根据token获取user对象全部信息
        User user = userService.findUserByUsername(JwtUtil.parse(token).getSubject());
        //将对象信息转为json字符串返回
        return JsonUtil.obj2String(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout(){

        System.out.println("successLogOut");
        return "success";
    }

    @GetMapping("/get_username_list")
    @ResponseBody
    public String getUsernameList() {
        return JsonUtil.obj2String(userService.getUsernameList());
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user) {
        user.setRole("user");
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        user.setIntroduction("这个人很懒，什么也没有留下。");
        int saveStatus = privilegeService.save(user);
        MsgBox msgBox = new MsgBox();
        if (saveStatus == 1) {
            msgBox.setCode(1);
            msgBox.setMsg("注册成功！");
        } else {
            msgBox.setCode(0);
            msgBox.setMsg("注册失败！");
        }
        return JsonUtil.obj2String(msgBox);
    }

    @GetMapping("/get_resident_name_by_username")
    @ResponseBody
    public String getResidentNameByUsername(String username) {
        return userService.getResidentNameByUsername(username);
    }
}
