package io.oicp.yorick61c.domain.body_exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;


@Data
@TableName("exam_item")
public class ExamItem {

    @TableId(type = IdType.AUTO)
    private Integer examId;
    private String examType;
    private String examName;
    private String examEquipment;
    private String description;
    private Integer[] examDataItemIds;
    private List<ExamDataItem> examDataItems;

}
