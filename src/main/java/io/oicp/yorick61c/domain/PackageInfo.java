package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
* 服务包信息实体类
* */
@Data
@TableName("package_info")
public class PackageInfo {
    @TableId(type = IdType.AUTO)
    private Integer packageId;
    private String packageName;
    private String packageNum;
    private String packageLevel;
    private String packageType;
    private String packageStatus;
    private String fitPeople;
    private Double packagePrice;
    private Double packageDiscountPrice; //服务包折扣价格
    private String serviceInfo; //服务说明
}
