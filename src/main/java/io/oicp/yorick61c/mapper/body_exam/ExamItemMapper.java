package io.oicp.yorick61c.mapper.body_exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.body_exam.ExamItem;

public interface ExamItemMapper extends BaseMapper<ExamItem> {

    int insertExamInfo(ExamItem info);

    int insertMappingInfo(int examId, int itemId);

    String selectExamNameByExamId(int examId);
}
