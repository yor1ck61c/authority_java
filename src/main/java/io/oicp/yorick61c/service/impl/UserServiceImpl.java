package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<User> getUsernameList() {
        return userMapper.selectList(new QueryWrapper<User>().select("username"));
    }
}
