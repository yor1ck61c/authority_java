package io.oicp.yorick61c.domain.body_exam;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("exam_data_item")
public class ExamDataItem {

    @TableId(type = IdType.AUTO)
    private Integer itemId;
    private String itemName;
    private Double minValue;
    private Double maxValue;
    private String unit;
    private String displayName;
    private String highValueMeans;
    private String lowValueMeans;
    private String description;

}
