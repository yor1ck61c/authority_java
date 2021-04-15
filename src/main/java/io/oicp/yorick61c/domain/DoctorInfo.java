package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("doctor_info")
public class DoctorInfo {
    @TableId(type = IdType.AUTO)
    private Integer doctorId;
    private String doctorImg;
    private String doctorName;
    private String doctorDuty;
    private String doctorTitle; //医生职称
    private String teamAdmin; //团队管理员
    private String otherInfo; //其他描述


}
