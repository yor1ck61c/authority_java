package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("exam_info")
public class ExamInfo {

    @TableId(type = IdType.AUTO)
    private Integer examId;
    private String examName;
    private String examType;
    private String examEquipment;
    private String examDescription;
    private List<DataItem> examDataItems;

}
