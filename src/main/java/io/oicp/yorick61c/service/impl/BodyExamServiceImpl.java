package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.body_exam.*;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.mapper.body_exam.*;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamDataItemInfo;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamDetailDto;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamResultDto;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CHandleExamDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.service.BodyExamService;
import io.oicp.yorick61c.utils.TimeUtil;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
    private ExamResultMapper examResultMapper;

    @Resource
    private ExamDataItemMapper examDataItemMapper;

    @Resource
    private ResidentBasicFileMapper residentBasicFileMapper;

    @Resource
    private ExaminationExamMappingMapper examinationExamMappingMapper;

    @Resource
    private ExamItemExamDataItemMappingMapper examItemExamDataItemMappingMapper;

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
        info.setResidentName(this.getResidentNameByUserId(info.getUserId()));
        info.setExamTime(TimeUtil.formatTime(info.getExamTime()));
        info.setHasExamined(false);
        int saveInfoStatus = examinationMapper.insertExamination(info);
        Integer examinationId = info.getExaminationId();
        Integer userId = info.getUserId();
        int insertUEMappingInfoStatus = examinationMapper.insertUEMappingInfo(userId, examinationId);
        Integer[] examItemIds = info.getExamItemIds();
        int sum = 0;
        for (Integer examItemId: examItemIds) {
            sum += examinationMapper.insertMappingInfo(examinationId, examItemId);
        }
        return saveInfoStatus != 0 && insertUEMappingInfoStatus != 0 && sum == examItemIds.length ? 1 : 0;
    }

    private String getResidentNameByUserId(Integer userId) {
        CBasicFileTableDto cBasicFileTableDto = residentBasicFileMapper.selectById(userId);
        return cBasicFileTableDto.getResidentName();
    }

    @Override
    @Transactional
    public List<CExamResultDto> getExamResultDtoList() {  //获取检查结果列表
        int userId = this.getUserId();
        long[] examinationIdList = examinationMapper.getExaminationIdListById(userId);
        List<CExamResultDto> resultDtoList = new ArrayList<>();
        for (long examinationId : examinationIdList) {

            List<ExaminationExamMapping> list = examinationExamMappingMapper.selectExamIdAndStatusByExaminationId((int) examinationId);

            for (ExaminationExamMapping exam : list) {
                CExamResultDto dto;
                if (exam.getHasValue()) {
                    dto = generateHasValueExamResultDto(exam.getExaminationId(), exam.getExamId());
                } else {
                    dto = generateUnprocessedExamResultDto(exam.getExaminationId(), exam.getExamId());
                }
                resultDtoList.add(dto);
            }

        }
        return resultDtoList;
    }

    private CExamResultDto generateHasValueExamResultDto(Integer examinationId, Integer examId) {
        CExamResultDto cExamResultDto = new CExamResultDto();
        Examination examination = examinationMapper.selectExamInfoById(examinationId);
        cExamResultDto.setExaminationId(examinationId);
        cExamResultDto.setExamId(examId);
        cExamResultDto.setResidentName(examination.getResidentName());
        cExamResultDto.setExamTime(examination.getExamTime());
        cExamResultDto.setHasExamined(true);
        cExamResultDto.setExamName(examItemMapper.selectExamNameByExamId(examId));
        return cExamResultDto;
    }

    private CExamResultDto generateUnprocessedExamResultDto(Integer examinationId, Integer examId) {
        CExamResultDto cExamResultDto = new CExamResultDto();
        Examination examination = examinationMapper.selectExamInfoById(examinationId);
        cExamResultDto.setExaminationId(examinationId);
        cExamResultDto.setExamId(examId);
        cExamResultDto.setResidentName(examination.getResidentName());
        cExamResultDto.setExamTime(examination.getExamTime());
        cExamResultDto.setHasExamined(false);
        cExamResultDto.setExamName(examItemMapper.selectExamNameByExamId(examId));
        return cExamResultDto;
    }

    @Override
    public List<CHandleExamDto> getUnprocessedExamList() {  //获取未处理的检查列表
        List<CHandleExamDto> cHandleExamDtoList = this.generateUnprocessedExamList();
        Collections.sort(cHandleExamDtoList);
        return cHandleExamDtoList;
    }

    @Override
    @Transactional
    public int deleteExam(int examinationId, int examId) {
        int deleteExamItemStatus = examinationExamMappingMapper.deleteExamByExaminationIdAndExamId(examinationId, examId);
        if (examinationExamMappingMapper.countExamItemsByExaminationId(examinationId) == 0) {
            int deleteExaminationStatus = examinationMapper.deleteById(examinationId);
            return deleteExamItemStatus & deleteExaminationStatus;
        }
        return deleteExamItemStatus;
    }

    @Override
    public List<CExamDataItemInfo> getExamDataItemInfoByExamId(int examinationId, int examId) {
        List<CExamDataItemInfo> res = new ArrayList<>();
        long[] itemIds = examItemExamDataItemMappingMapper.selectExamDataItemIdByExamId(examId);
        for (long itemId : itemIds) {
            res.add(generateExamDataItemById(examinationId, examId, itemId));
        }
        return res;
    }

    @Override
    @Transactional
    public int saveExamValue(List<CExamDataItemInfo> examDataItemInfoList) {

        for (CExamDataItemInfo data : examDataItemInfoList) {
            examItemExamDataItemMappingMapper.saveExamValue(data.getExaminationId(),data.getExamId(), data.getItemId(), data.getItemValue());
        }
        CExamDataItemInfo info = examDataItemInfoList.get(0);
        examinationExamMappingMapper.setHandle(info.getExaminationId(), info.getExamId());

        List<ExaminationExamMapping> has_value = examinationExamMappingMapper.selectList(new QueryWrapper<ExaminationExamMapping>().select("has_value"));
        int sum = 0;
        for (ExaminationExamMapping examinationExamMapping : has_value) {
            if (examinationExamMapping.getHasValue()) sum++;
        }
        if (sum == has_value.size()) {
            examinationMapper.setHandle(examDataItemInfoList.get(0).getExaminationId());
        }
        return 1;
    }

    @Override
    public List<CExamDetailDto> getExamDetailList(int examinationId, int examId) {
        List<ExamResult> idValueList = examResultMapper.selectIdValueList(examinationId, examId);
        List<CExamDetailDto> res = new ArrayList<>();
        for (ExamResult examResult : idValueList) {
            res.add(generateExamResultDto(examResult.getItemId(), examResult.getItemValue()));
        }
        return res;
    }

    @Override
    @Transactional
    public int saveExamResultFromExcel(List<ExamResult> examResults) {
        int sum = 0;
        for (ExamResult i: examResults) {
            sum += examResultMapper.updateExamResult(i);
        }
        return sum == examResults.size() ? 1 : 0;
    }

    private CExamDetailDto generateExamResultDto(Integer itemId, Double itemValue) {
        CExamDetailDto cExamDetailDto = new CExamDetailDto();
        ExamDataItem item = dataItemMapper.selectById(itemId);
        cExamDetailDto.setItemValue(itemValue);
        cExamDetailDto.setItemName(item.getItemName());
        cExamDetailDto.setUnit(item.getUnit());
        cExamDetailDto.setItemId(itemId);
        String range = item.getMinValue() + "-" + item.getMaxValue();
        cExamDetailDto.setItemRange(range);
        return cExamDetailDto;
    }

    private CExamDataItemInfo generateExamDataItemById(int examinationId, int examId, long itemId) {
        ExamDataItem examDataItem = examDataItemMapper.selectById(itemId);
        CExamDataItemInfo cExamDataItemInfo = new CExamDataItemInfo();

        cExamDataItemInfo.setExaminationId(examinationId);
        cExamDataItemInfo.setExamId(examId);
        cExamDataItemInfo.setItemId(examDataItem.getItemId());
        cExamDataItemInfo.setItemName(examDataItem.getItemName());
        cExamDataItemInfo.setUnit(examDataItem.getUnit());
        return cExamDataItemInfo;
    }



    private List<CHandleExamDto> generateUnprocessedExamList() {
        List<Examination> examinationList = examinationMapper.selectList(new QueryWrapper<Examination>()
                .select("examination_id", "resident_name", "exam_time", "user_id", "has_examined")); //获取所有检查
        List<CHandleExamDto> unprocessedExamList = new ArrayList<>();  //创建返回list
        // 获取单个检查
        for (Examination examination : examinationList) {
              // 如果检查没有结果，先获取检查id，然后根据检查id获取检查项id，然后为每个检查项封装一个对象
            long[] examIds = examinationExamMappingMapper.selectUnDoExamIdByExaminationId(examination.getExaminationId());
            for (long examId : examIds) {
                CHandleExamDto cHandleExamDto = new CHandleExamDto();
                cHandleExamDto.setExamId((int) examId);
                cHandleExamDto.setExamName(examItemMapper.selectExamNameByExamId((int) examId));
                cHandleExamDto.setExamTime(examination.getExamTime());
                cHandleExamDto.setHasExamined(false);
                cHandleExamDto.setExaminationId(examination.getExaminationId());
                cHandleExamDto.setResidentName(examination.getResidentName());
                unprocessedExamList.add(cHandleExamDto);
            }

        }
        return unprocessedExamList;
    }


    @Override
    public List<ExamItem> getExamItemNameList() {
        return examItemMapper.selectList(new QueryWrapper<ExamItem>().select("exam_id", "exam_name"));
    }

    private int getUserId() {    // 用户名不重复，唯一确定，安全。
        String currentUserName = UserContext.getCurrentUserName();
        User userByUsername = userMapper.findUserByUsername(currentUserName);
        return userByUsername.getId();
    }

}
