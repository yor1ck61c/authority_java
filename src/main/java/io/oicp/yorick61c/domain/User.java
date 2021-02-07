package io.oicp.yorick61c.domain;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String introduction;
}
