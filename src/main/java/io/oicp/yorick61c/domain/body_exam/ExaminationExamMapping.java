package io.oicp.yorick61c.domain.body_exam;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("examination_exam_item_mapping")
public class ExaminationExamMapping {
    private Integer examinationId;
    private Integer examId;
    private Boolean hasValue;
}
