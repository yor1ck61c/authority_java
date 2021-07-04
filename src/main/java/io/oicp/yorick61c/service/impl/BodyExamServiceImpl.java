package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.oicp.yorick61c.domain.body_exam.*;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.mapper.body_exam.*;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.*;
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
    private ExamOrderMapper examOrderMapper;

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

    @Override
    @Transactional
    public int saveExaminationFromOrder(CNewExamOrderDto info) {
        Examination examination = new Examination();
        examination.setExamTime(info.getExamTime());
        examination.setHasExamined(false);
        examination.setDoctorName(info.getDoctorName());
        examination.setUserId(info.getUserId());
        examination.setResidentName(info.getResidentName());
        int saveInfoStatus = examinationMapper.insertExamination(examination);
        Integer examinationId = examination.getExaminationId();
        examinationMapper.insertMappingInfo(examinationId, info.getExamId());
        examinationMapper.insertUEMappingInfo(info.getUserId(), examinationId);
        examOrderMapper.deleteById(info.getExamOrderId());
        return saveInfoStatus;
    }

    @Override
    public List<ExamDataItem> getAllExamDataItemsInfo() {
        return dataItemMapper.selectList(null);
    }

    @Override
    @Transactional
    public int deleteExamDataItemById(int itemId) {
        examItemExamDataItemMappingMapper.deleteMappingByItemId(itemId);
        return dataItemMapper.deleteById(itemId);
    }

    @Override
    public int updateExamDataItemById(ExamDataItem item) {
        return dataItemMapper.updateById(item);
    }

    @Override
    @Transactional
    public List<ExamItem> getAllExamItemsInfo() {
        QueryWrapper<ExamItem> examItemQueryWrapper = new QueryWrapper<>();
        examItemQueryWrapper.select("exam_id","exam_type", "exam_name", "exam_equipment", "description");
        List<ExamItem> examItems = examItemMapper.selectList(examItemQueryWrapper);
        for (ExamItem item: examItems) {
            long[] ids = examItemExamDataItemMappingMapper.selectExamDataItemIdByExamId(item.getExamId());
            item.setExamDataItemIds(convertLongArray2Int(ids));
        }
        return examItems;
    }

    private Integer[] convertLongArray2Int(long[] array) {
        Integer[] res = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = (int) array[i];
        }
        return res;
    }

    @Override
    @Transactional
    public int deleteExamItemById(int examId) {
        examinationExamMappingMapper.deleteMappingByExamItemId(examId);
        return examItemMapper.deleteById(examId);
    }

    @Override
    @Transactional
    public int updateExamItemById(ExamItem item) {
        examItemMapper.clearMappingByExamId(item.getExamId());
        UpdateWrapper<ExamItem> examItemUpdateWrapper = new UpdateWrapper<>();
        examItemUpdateWrapper.eq("exam_id", item.getExamId());
        examItemUpdateWrapper.set("exam_type", item.getExamType());
        examItemUpdateWrapper.set("exam_name", item.getExamName());
        examItemUpdateWrapper.set("exam_equipment", item.getExamEquipment());
        examItemUpdateWrapper.set("description", item.getDescription());
        /*QueryWrapper<ExamItem> examItemQueryWrapper = new QueryWrapper<>();
        examItemQueryWrapper.eq("exam_id", item.getExamId());
        examItemQueryWrapper.select("exam_type", "exam_name", "exam_equipment", "description");*/
        Integer[] examDataItemIds = item.getExamDataItemIds();
        for (Integer examDataItemId : examDataItemIds) {
            examItemMapper.insertMappingInfo(item.getExamId(), examDataItemId);
        }
        return examItemMapper.update(null, examItemUpdateWrapper);
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

    @Override
    @Transactional
    public int saveOrderList(CExamOrderDto dto) {
        Integer[] examItemIds = dto.getExamItemIds();
        int sum = 0;
        for (Integer examItemId : examItemIds) {
            sum += examOrderMapper.insert(this.generateExamOrderItem(
                    examItemId,
                    dto.getUsername(),
                    dto.getExamTime(),
                    dto.getResidentName()));
        }
        return sum == examItemIds.length ? 1 : 0 ;
    }

    @Override
    public List<ExamOrder> getExamOrderList() {
        return examOrderMapper.selectList(null);
    }

    @Override
    public int deleteExamOrderById(int examOrderId) {
        return examOrderMapper.deleteById(examOrderId);
    }



    private ExamOrder generateExamOrderItem(Integer examItemId, String username, String examTime, String residentName) {
        ExamOrder examOrder = new ExamOrder();
        String examName = examItemMapper.selectExamNameByExamId(examItemId);
        examOrder.setExamId(examItemId);
        examOrder.setExamName(examName);
        examOrder.setUserId(userMapper.findUserByUsername(username).getId());
        examOrder.setExamTime(TimeUtil.formatTime(examTime));
        examOrder.setResidentName(residentName);
        return examOrder;
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
