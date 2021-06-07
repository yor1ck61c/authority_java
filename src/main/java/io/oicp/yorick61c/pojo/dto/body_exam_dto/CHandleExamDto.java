package io.oicp.yorick61c.pojo.dto.body_exam_dto;

import lombok.Data;

@Data
public class CHandleExamDto implements Comparable<CHandleExamDto>{

    private Integer examinationId;
    private Integer examId;
    private String residentName;
    private String examName;
    private String examTime;
    private Boolean hasExamined;

    @Override
    public int compareTo(CHandleExamDto o) {
        return 0;
    }
}
