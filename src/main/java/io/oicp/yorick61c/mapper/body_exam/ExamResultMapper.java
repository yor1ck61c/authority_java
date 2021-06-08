package io.oicp.yorick61c.mapper.body_exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.body_exam.ExamResult;

import java.util.List;

public interface ExamResultMapper extends BaseMapper<ExamResult> {
    List<ExamResult> selectIdValueList(int examinationId, int examId);

    int updateExamResult(ExamResult i);
}
