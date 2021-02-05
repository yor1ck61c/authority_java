package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    @GetMapping("/login")
    public User login(@RequestBody User user){
        return userService.login(user);
    }
}
