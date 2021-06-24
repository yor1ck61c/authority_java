package io.oicp.yorick61c.pojo.dto.build_file_dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("resident_other_info")
public class CResidentBuildFileOtherInfoDto {

    @TableId
    private Integer userId;

    private String medicineAllergyHistoryList; //药物过敏史
    private String otherMedicineAllergySource;
    private String foodAllergyHistoryList;
    private String otherFoodAllergyHistory;
    private String environmentAllergyHistoryList;

    private String otherEnvironmentAllergyHistory;
    private String mixedAllergyHistory;
    private String exposedHistoryList;
    private String diseaseHistoryList;
    private String otherDiseaseHistory;

    private String traumaHistory;
    private String bloodInHistory;
    private String fatherDiseaseHistoryList;
    private String fatherOtherDiseaseHistory;
    private String motherDiseaseHistoryList;

    private String motherOtherDiseaseHistory;
    private String brotherDiseaseHistoryList;
    private String brotherOtherDiseaseHistory;
    private String childrenDiseaseHistoryList;
    private String childrenOtherDiseaseHistory;
}
