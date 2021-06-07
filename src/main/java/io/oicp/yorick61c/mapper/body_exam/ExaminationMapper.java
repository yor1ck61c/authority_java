package io.oicp.yorick61c.mapper.body_exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.body_exam.Examination;

public interface ExaminationMapper extends BaseMapper<Examination> {

    int insertExamination(Examination info);

    int insertMappingInfo(int examinationId, int examId);

    int insertUEMappingInfo(int userId, Integer examinationId);

    long[] getExaminationIdListById(int userId);

    String[] getExamNameListByExaminationId(long examinationId);

    Examination selectExamInfoById(long examinationId);

    void setHandle(Integer examinationId);
}
