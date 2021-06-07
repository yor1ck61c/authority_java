package io.oicp.yorick61c.pojo.dto.body_exam_dto;

import lombok.Data;

@Data
public class CExamDetailDto {

    private Integer itemId;
    private String itemName;
    private Double itemValue;
    private String unit;
    private String itemRange;
}
