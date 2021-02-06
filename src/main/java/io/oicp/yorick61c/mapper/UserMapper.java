package io.oicp.yorick61c.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User login(User user);

    User findUserByUsername(String username);
}
