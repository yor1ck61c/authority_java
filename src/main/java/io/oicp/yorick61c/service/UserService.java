package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.login.User;

public interface UserService {

    User login(User user);

    User findUserByUsername(String username);
}
