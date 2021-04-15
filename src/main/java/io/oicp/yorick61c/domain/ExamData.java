package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("exam_data")
public class ExamData {

    private Integer userId;
    private Date examTime;
    private String examItems;
    private String examValues;
    private String analyseRes;
}
