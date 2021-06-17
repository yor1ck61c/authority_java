package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.body_exam.*;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.*;

import java.util.List;

public interface BodyExamService {
    int saveDataItem(ExamDataItem info);

    List<ExamDataItem> getItemNameList();

    int saveExamInfo(ExamItem info);

    List<ExamItem> getExamItemNameList();

    int saveExamination(Examination info);

    List<CExamResultDto> getExamResultDtoList();

    List<CHandleExamDto> getUnprocessedExamList();

    int deleteExam(int examinationId, int examId);

    List<CExamDataItemInfo> getExamDataItemInfoByExamId(int examinationId, int examId);

    int saveExamValue(List<CExamDataItemInfo> examDataItemInfoList);

    List<CExamDetailDto> getExamDetailList(int examinationId, int examId);

    int saveExamResultFromExcel(List<ExamResult> examResults);

    int saveOrderList(CExamOrderDto dto);

    List<ExamOrder> getExamOrderList();

    int deleteExamOrderById(int examOrderId);

    int saveExaminationFromOrder(CNewExamOrderDto info);

    List<ExamDataItem> getAllExamDataItemsInfo();

    int deleteExamDataItemById(int itemId);

    int updateExamDataItemById(ExamDataItem item);

    List<ExamItem> getAllExamItemsInfo();

    int deleteExamItemById(int examId);

    int updateExamItemById(ExamItem item);
}
