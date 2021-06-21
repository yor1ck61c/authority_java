package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.service.UserService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource(description = "UserMapper")
    private UserMapper userMapper;

    @Resource
    ResidentBasicFileMapper residentBasicFileMapper;

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

    @Override
    public String getResidentNameByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        CBasicFileTableDto cBasicFileTableDto = residentBasicFileMapper.selectById(user.getId());
        MsgBox msgBox = new MsgBox();
        try {
            msgBox.setCode(200);
            msgBox.setMsg(cBasicFileTableDto.getResidentName());
            return JsonUtil.obj2String(msgBox);
        } catch (NullPointerException e) {
            msgBox.setCode(501);
            return JsonUtil.obj2String(msgBox);
        }
    }
}
