package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.body_exam.ExamDataItem;
import io.oicp.yorick61c.domain.body_exam.ExamItem;
import io.oicp.yorick61c.domain.body_exam.Examination;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamResultDto;

import java.util.List;

public interface BodyExamService {
    int saveDataItem(ExamDataItem info);

    List<ExamDataItem> getItemNameList();

    int saveExamInfo(ExamItem info);

    List<ExamItem> getExamItemNameList();

    int saveExamination(Examination info);

    List<CExamResultDto> getExamResultDtoList();
}
