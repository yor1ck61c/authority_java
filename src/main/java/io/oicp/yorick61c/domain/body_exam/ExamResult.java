package io.oicp.yorick61c.domain.body_exam;


import lombok.Data;

@Data
public class ExamResult {

    private Integer examinationId;
    private Integer examId;
    private Integer itemId;
    private Double itemValue;

}
