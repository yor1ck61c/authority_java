package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.login.User;

import java.util.List;

public interface UserService {

    User login(User user);

    User findUserByUsername(String username);

    List<User> getUsernameList();
}
