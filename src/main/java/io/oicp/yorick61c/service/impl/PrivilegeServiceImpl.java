package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.PrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("PrivilegeService")
public class PrivilegeServiceImpl implements PrivilegeService{

    @Resource(description = "UserMapper")
    private UserMapper userMapper;

    @Override
    public List<User> getAccountInfo() {

        return userMapper.selectList(new QueryWrapper<User>()
                .select("id","username","password","role","avatar","introduction"));
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }
}
