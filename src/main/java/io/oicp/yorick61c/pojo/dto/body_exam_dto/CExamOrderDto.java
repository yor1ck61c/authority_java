package io.oicp.yorick61c.pojo.dto.body_exam_dto;

import lombok.Data;

@Data
public class CExamOrderDto {
    private Integer orderId;
    private Integer[] examItemIds;
    private Integer userId;
    private String username;
    private String residentName;
    private String examTime;
}
