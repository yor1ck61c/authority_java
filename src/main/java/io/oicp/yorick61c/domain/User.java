package io.oicp.yorick61c.domain;

import lombok.Data;

@Data  //Lombok注释，自动生成字段对应getter/setter，无参构造，toString(),equals(),hashcode()
public class User {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String introduction;
}
