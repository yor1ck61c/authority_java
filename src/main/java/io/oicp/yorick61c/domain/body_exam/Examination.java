package io.oicp.yorick61c.domain.body_exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("examination")
public class Examination {

    @TableId(type = IdType.AUTO)
    private Integer examinationId;
    private Integer userId;
    private String residentName;
    private String examTime;
    private String doctorName;
    private Boolean hasExamined;
    private Integer[] examItemIds;
}
