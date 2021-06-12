package io.oicp.yorick61c.domain.body_exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("exam_order_cache")
public class ExamOrder {
    @TableId(type = IdType.AUTO)
    private Integer examOrderId;
    private Integer examId;
    private Integer userId;
    private String residentName;
    private String examTime;
    private String examName;
}
