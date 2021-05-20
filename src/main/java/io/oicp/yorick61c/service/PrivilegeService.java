package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.login.User;

import java.util.List;

public interface PrivilegeService {

    List<User> getAccountInfo();

    void save(User user);

    void delete(Integer id);

    void update(User user);
}
