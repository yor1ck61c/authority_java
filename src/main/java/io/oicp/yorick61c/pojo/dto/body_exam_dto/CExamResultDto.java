package io.oicp.yorick61c.pojo.dto.body_exam_dto;

import lombok.Data;

@Data
public class CExamResultDto {

    private Integer examinationId;
    private String residentName;
    private String examName;
    private Boolean hasExamined;
    private String examTime;
}
