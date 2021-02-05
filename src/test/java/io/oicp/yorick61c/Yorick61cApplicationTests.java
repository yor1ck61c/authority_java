package io.oicp.yorick61c;

import io.oicp.yorick61c.controller.UserController;
import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Yorick61cApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    @Test
    void testSelect() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("111111");
        System.out.println(userController.login(user));
    }

}
