package io.oicp.yorick61c.mapper.body_exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.body_exam.ExaminationExamMapping;

import java.util.List;

public interface ExaminationExamMappingMapper extends BaseMapper<ExaminationExamMapping> {
    long[] selectUnDoExamIdByExaminationId(Integer examinationId);

    int deleteExamByExaminationIdAndExamId(int examinationId, int examId);

    int countExamItemsByExaminationId(int examinationId);

    void setHandle(Integer examinationId, Integer examId);

    long[] selectExamIdByExaminationId(int examinationId);

    List<ExaminationExamMapping> selectExamIdAndStatusByExaminationId(int examinationId);
}
