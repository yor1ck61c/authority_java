package io.oicp.yorick61c;

import io.oicp.yorick61c.controller.PrivilegeController;
import io.oicp.yorick61c.controller.UserController;
import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.UserService;
import io.oicp.yorick61c.utils.JsonUtil;
import io.oicp.yorick61c.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Yorick61cApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeController privilegeController;
    @Test
    void contextLoads() {
    }

    @Test
    void testSelect() {
        String accountInfo = privilegeController.getAccountInfo();
        System.out.println(accountInfo);
    }

    @Test
    void testJwt() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("111111");
        String token = JwtUtil.generateToken(userService.login(user).getUsername());
        System.out.println(token);
        System.out.println(JwtUtil.parse(token).getSubject());
    }

    @Test
    void testJsonUtil() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("111111");
        System.out.println(JsonUtil.obj2String(user));
    }

}
