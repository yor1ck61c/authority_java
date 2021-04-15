package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data  //Lombok注释，自动生成字段对应getter/setter，无参构造，toString(),equals(),hashcode()
@TableName(value = "user") //表名与实体类名不一致时需添加
public class TestUser {

    //设置id字段自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String[] roles;
    private String avatar;
    private String introduction;
}
