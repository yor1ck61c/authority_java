package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.body_exam.ExamDataItem;
import io.oicp.yorick61c.domain.body_exam.ExamItem;
import io.oicp.yorick61c.domain.body_exam.ExamResult;
import io.oicp.yorick61c.domain.body_exam.Examination;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamDataItemInfo;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamDetailDto;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamResultDto;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CHandleExamDto;

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
}
