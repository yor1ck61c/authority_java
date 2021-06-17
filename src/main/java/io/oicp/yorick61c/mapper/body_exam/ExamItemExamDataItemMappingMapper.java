package io.oicp.yorick61c.mapper.body_exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.domain.body_exam.ExamItemExamDataItemMapping;
import org.apache.ibatis.annotations.Param;

public interface ExamItemExamDataItemMappingMapper extends BaseMapper<ExamItemExamDataItemMapping> {

    long[] selectExamDataItemIdByExamId(int examId);

    void saveExamValue(@Param("examinationId")Integer examinationId, @Param("examId") Integer examId, @Param("itemId") Integer itemId, @Param("itemValue") Double itemValue);

    void deleteMappingByItemId(int itemId);
}
