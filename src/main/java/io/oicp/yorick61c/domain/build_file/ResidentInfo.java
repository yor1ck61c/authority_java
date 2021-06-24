package io.oicp.yorick61c.domain.build_file;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "resident_info")
public class ResidentInfo {

    private Integer userId;
    private String username;
    private String residentName;
    private String fileNum; //档案编号
    private String residentId; //身份证号
    private String residentGender;
    private Integer residentAge;
    private Date residentBirth;
    private String registerAddrType;  //常住类型
    private String residentNation; //民族
    private String residentPhoneNum;
    private String registerAddr; //户籍所在地
    private String presentAddr; //目前所在地
    private String firmedDisease;
    private String residentBloodType;
    private String residentRhType;
    private String married; //婚姻状况
    private String eduLevel; //受教育情况
    private String profession;
    private String payment; //支付方式
    private String buildFileTime; //建档时间
    private String buildFileInstitute; //建档机构
    private String buildFileResident; //建档人
    private String reportCycle; //报告周期

    private String[] medicineAllergyHistoryList; //药物过敏史
    private String otherMedicineAllergySource;
    private String[] foodAllergyHistoryList;
    private String otherFoodAllergyHistory;
    private String[] environmentAllergyHistoryList;
    private String otherEnvironmentAllergyHistory;
    private String mixedAllergyHistory;
    private String[] exposedHistoryList;
    private String[] diseaseHistoryList;
    private String otherDiseaseHistory;
    private String traumaHistory;
    private String bloodInHistory;
    private String[] fatherDiseaseHistoryList;
    private String fatherOtherDiseaseHistory;
    private String[] motherDiseaseHistoryList;
    private String motherOtherDiseaseHistory;
    private String[] brotherDiseaseHistoryList;
    private String brotherOtherDiseaseHistory;
    private String[] childrenDiseaseHistoryList;
    private String childrenOtherDiseaseHistory;
}
