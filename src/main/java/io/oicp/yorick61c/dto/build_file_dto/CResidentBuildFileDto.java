package io.oicp.yorick61c.dto.build_file_dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("resident_info")
public class CResidentBuildFileDto {

    @TableId
    private Integer userId;
    private String residentName;
    private String fileNum; //档案编号
    private String residentId; //身份证号
    private String residentGender;
    private Integer residentAge;
    private Date residentBirth;
    private String registerAddrType;  //常住类型
    private String residentNation; //民族
    private String residentPhoneNum;
    private String contactName;
    private String contactPhoneNum;
    private String registerAddr; //户籍所在地
    private String presentAddr; //目前所在地
    private String firmedDisease; //确诊疾病
    private String residentBloodType;
    private String residentRhType;
    private String married; //婚姻状况
    private String eduLevel; //受教育情况
    private String profession;
    private String payment; //支付方式
    private String buildFileResident; //建档人
    private Date buildFileTime; //建档时间

}
