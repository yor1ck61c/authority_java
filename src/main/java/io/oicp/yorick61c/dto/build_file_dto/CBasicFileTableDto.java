package io.oicp.yorick61c.dto.build_file_dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("resident_info")
public class CBasicFileTableDto {

    @TableId
    private Integer userId;
    private String residentName;
    private String residentId; //身份证号
    private String residentGender;
    private Integer residentAge;
    private String residentPhoneNum;
    private String firmedDisease; //确诊疾病
    private String buildFileResident; //建档人
    private Date buildFileTime; //建档时间
    private String buildFileInstitute; //建档机构

}
