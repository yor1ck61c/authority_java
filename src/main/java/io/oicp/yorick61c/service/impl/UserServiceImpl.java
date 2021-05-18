package io.oicp.yorick61c.service.impl;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource(description = "UserMapper")
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public User findUserByUsername(String username) {



        return userMapper.findUserByUsername(username);
    }
}
