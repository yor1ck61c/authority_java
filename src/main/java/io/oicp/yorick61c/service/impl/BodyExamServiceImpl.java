package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.body_exam.ExamDataItem;
import io.oicp.yorick61c.domain.body_exam.ExamItem;
import io.oicp.yorick61c.domain.body_exam.Examination;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.mapper.body_exam.ExamDataItemMapper;
import io.oicp.yorick61c.mapper.body_exam.ExamItemMapper;
import io.oicp.yorick61c.mapper.body_exam.ExaminationMapper;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamResultDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.service.BodyExamService;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BodyExamServiceImpl implements BodyExamService {

    @Resource
    private ExamDataItemMapper dataItemMapper;

    @Resource
    private ExamItemMapper examItemMapper;

    @Resource
    private ExaminationMapper examinationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResidentBasicFileMapper residentBasicFileMapper;

    @Override
    public int saveDataItem(ExamDataItem info) {
        return dataItemMapper.insert(info);
    }

    @Override
    public List<ExamDataItem> getItemNameList() {
        return dataItemMapper.selectList(new QueryWrapper<ExamDataItem>().select("item_id","item_name"));
    }

    @Override
    public int saveExamInfo(ExamItem info) {
        int saveInfoStatus = examItemMapper.insertExamInfo(info);  // 插入一条记录并返回该记录的自增id并存入实体对象
        Integer examId = info.getExamId();  //获取该条记录的id
        Integer[] examDataItemIds = info.getExamDataItemIds();
        int sum = 0;
        for (Integer examDataItemId : examDataItemIds) {
            sum += examItemMapper.insertMappingInfo(examId, examDataItemId);
        }
        return saveInfoStatus != 0 && sum == examDataItemIds.length ? 1 : 0;
    }

    @Override
    @Transactional //开启事务，要么同时成功，要么同时失败。
    public int saveExamination(Examination info) {
        info.setExamTime(formatTime(info.getExamTime()));
        info.setHasExamined(false);
        int saveInfoStatus = examinationMapper.insertExamination(info);
        Integer examinationId = info.getExaminationId();
        int userId = this.getUserId(info.getResidentName());
        int insertUEMappingInfoStatus = examinationMapper.insertUEMappingInfo(userId, examinationId);
        Integer[] examItemIds = info.getExamItemIds();
        int sum = 0;
        for (Integer examItemId: examItemIds) {
            sum += examinationMapper.insertMappingInfo(examinationId, examItemId);
        }
        return saveInfoStatus != 0 && insertUEMappingInfoStatus != 0 && sum == examItemIds.length ? 1 : 0;
    }

    @Override
    public List<CExamResultDto> getExamResultDtoList() {
        int userId = this.getUserId();
        long[] examinationIdList = examinationMapper.getExaminationIdListById(userId);
        for (long i : examinationIdList) {
            System.out.println(i);
        }

        return null;
    }

    @Override
    public List<ExamItem> getExamItemNameList() {
        return examItemMapper.selectList(new QueryWrapper<ExamItem>().select("exam_id", "exam_name"));
    }

    public String formatTime(String time) {
        char[] chars = time.toCharArray();
        chars[10] = ' ';
        chars[12] += 8;
        return new String(chars).substring(0, 19);
    }

    public int getUserId() {
        String currentUserName = UserContext.getCurrentUserName();
        User userByUsername = userMapper.findUserByUsername(currentUserName);
        return userByUsername.getId();
    }

    public int getUserId(String username) {
        CBasicFileTableDto residentInfo = residentBasicFileMapper.selectOne(new QueryWrapper<CBasicFileTableDto>().eq("resident_name", username));
        return residentInfo.getUserId();
    }
}
