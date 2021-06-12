package io.oicp.yorick61c.pojo.dto.body_exam_dto;

import lombok.Data;

@Data
public class CNewExamOrderDto {
    private Integer examOrderId;
    private Integer userId;
    private String residentName;
    private String examTime;
    private String doctorName;
    private Boolean hasExamined;
    private Integer examId;
}
