package io.oicp.yorick61c.controller;


import io.oicp.yorick61c.domain.body_exam.*;
import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.*;
import io.oicp.yorick61c.service.BodyExamService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/exam")
public class BodyExamController {

    @Resource(description = "bodyExamService")
    private BodyExamService bodyExamService;

    @PostMapping("/save_data_item")
    @ResponseBody
    public String saveExamDataItemInfo(@RequestBody ExamDataItem info) {
        int insertStatus = bodyExamService.saveDataItem(info);
        return getSaveMsgString(insertStatus);
    }

    @PostMapping("/save_exam_info")
    @ResponseBody
    public String saveExamInfo(@RequestBody ExamItem info) {
        int insertStatus = bodyExamService.saveExamInfo(info);
        return getSaveMsgString(insertStatus);
    }
    @PostMapping("/save_excel_data")
    @ResponseBody
    public String saveExcelData(@RequestBody List<ExamDataItem> examDataItems) {
        int insertStatus = 0;
        for (ExamDataItem info : examDataItems){
            insertStatus = bodyExamService.saveDataItem(info);
            if (insertStatus == 0){break;}
        }
        return getSaveMsgString(insertStatus);
    }

    @PostMapping("/save_exam_result_data_from_excel")
    @ResponseBody
    public String saveExamResultDataFromExcel(@RequestBody List<ExamResult> examResults) {
        int res = bodyExamService.saveExamResultFromExcel(examResults);

        return getSaveMsgString(res);
    }

    @PutMapping("/exam_data_item")
    @ResponseBody
    public String updateExamDataItemById(@RequestBody ExamDataItem item) {
        int res = bodyExamService.updateExamDataItemById(item);
        return getUpdateMsgString(res);
    }

    @PutMapping("/exam_item")
    @ResponseBody
    public String updateExamItemById(@RequestBody ExamItem item) {
        int res = bodyExamService.updateExamItemById(item);
        return getUpdateMsgString(res);
    }


    @PostMapping("/save_examination")
    @ResponseBody
    public String saveExamination(@RequestBody Examination info) {
        int insertStatus = bodyExamService.saveExamination(info);
        return getSaveMsgString(insertStatus);
    }

    @PostMapping("/save_examination_from_order")
    @ResponseBody
    public String saveExaminationFromOrder(@RequestBody CNewExamOrderDto info) {
        int insertStatus = bodyExamService.saveExaminationFromOrder(info);
        return getSaveMsgString(insertStatus);
    }


    @GetMapping("/get_item_names")
    @ResponseBody
    public String getItemNameList() {
        List<ExamDataItem> nameList = bodyExamService.getItemNameList();
        return JsonUtil.obj2String(nameList);
    }

    @GetMapping("/get_exam_data_items_info")
    @ResponseBody
    public String getExamDataItemsInfo() {
        List<ExamDataItem> examDataItemList = bodyExamService.getAllExamDataItemsInfo();
        return JsonUtil.obj2String(examDataItemList);
    }

    @GetMapping("/get_exam_items_info")
    @ResponseBody
    public String getExamItemsInfo() {
        List<ExamItem> examItemList = bodyExamService.getAllExamItemsInfo();
        return JsonUtil.obj2String(examItemList);
    }

    @GetMapping("/delete_exam_data_item_by_id")
    @ResponseBody
    public String deleteExamDataItemById(int itemId) {
        int status = bodyExamService.deleteExamDataItemById(itemId);
        return JsonUtil.obj2String(getDeleteMsgString(status));
    }

    @GetMapping("/delete_exam_item_by_id")
    @ResponseBody
    public String deleteExamItemById(int examId) {
        int status = bodyExamService.deleteExamItemById(examId);
        return JsonUtil.obj2String(getDeleteMsgString(status));
    }

    @GetMapping("/get_exam_item_names")
    @ResponseBody
    public String getExamItemNameList() {
        List<ExamItem> nameList = bodyExamService.getExamItemNameList();
        return JsonUtil.obj2String(nameList);
    }

    @GetMapping("/get_exam_result")
    @ResponseBody
    public String getExamResultList() {
        List<CExamResultDto> resultDtoList = bodyExamService.getExamResultDtoList();
        return JsonUtil.obj2String(resultDtoList);
    }

    @GetMapping("/get_unprocessed_exam_list")
    @ResponseBody
    public String getUnprocessedExamList() {
        List<CHandleExamDto> res = bodyExamService.getUnprocessedExamList();
        return JsonUtil.obj2String(res);
    }

    @GetMapping("/get_exam_data_item_info_by_exam_id")
    @ResponseBody
    public String getExamDataItemInfoByExamId(int examinationId, int examId) {
        List<CExamDataItemInfo> res = bodyExamService.getExamDataItemInfoByExamId(examinationId, examId);
        return JsonUtil.obj2String(res);
    }

    @GetMapping("/get_exam_detail")
    @ResponseBody
    public String getExamDetail(int examinationId, int examId) {
        List<CExamDetailDto> res = bodyExamService.getExamDetailList(examinationId, examId);
        return JsonUtil.obj2String(res);
    }

    @PostMapping("/delete_exam")
    @ResponseBody
    public String deleteExam(int examinationId, int examId) {
        int res = bodyExamService.deleteExam(examinationId, examId);
        return getDeleteMsgString(res);
    }

    @PostMapping("/save_exam_value")
    @ResponseBody
    public String saveExamValue(@RequestBody List<CExamDataItemInfo> examDataItemInfoList) {
        int res = bodyExamService.saveExamValue(examDataItemInfoList);
        return getSaveMsgString(res);
    }

    @PostMapping("/save_order_list")
    @ResponseBody
    public String saveOrderList(@RequestBody CExamOrderDto dto) {
        int res = bodyExamService.saveOrderList(dto);
        return getSaveOrderMsgString(res);
    }


    @GetMapping("/delete_exam_order")
    @ResponseBody
    public String deleteExamOrder(int examOrderId) {
        int res = bodyExamService.deleteExamOrderById(examOrderId);
        return getDeleteMsgString(res);
    }

    @GetMapping("/get_exam_order_list")
    @ResponseBody
    public String getExamOrderList() {
        List<ExamOrder> examOrderList = bodyExamService.getExamOrderList();
        return JsonUtil.obj2String(examOrderList);
    }

    private String getSaveMsgString(int insertStatus) {
        MsgBox msg = new MsgBox();
        if (insertStatus == 0) {
            msg.setCode(0);
            msg.setMsg("保存失败");
        } else {
            msg.setCode(1);
            msg.setMsg("保存成功");
        }
        return JsonUtil.obj2String(msg);
    }

    private String getUpdateMsgString(int insertStatus) {
        MsgBox msg = new MsgBox();
        if (insertStatus == 0) {
            msg.setCode(0);
            msg.setMsg("更改失败");
        } else {
            msg.setCode(1);
            msg.setMsg("更改成功");
        }
        return JsonUtil.obj2String(msg);
    }

    private String getSaveOrderMsgString(int insertStatus) {
        MsgBox msg = new MsgBox();
        if (insertStatus == 0) {
            msg.setCode(0);
            msg.setMsg("预约失败");
        } else {
            msg.setCode(1);
            msg.setMsg("预约成功");
        }
        return JsonUtil.obj2String(msg);
    }

    private String getDeleteMsgString(int insertStatus) {
        MsgBox msg = new MsgBox();
        if (insertStatus == 0) {
            msg.setCode(0);
            msg.setMsg("删除失败");
        } else {
            msg.setCode(1);
            msg.setMsg("删除成功");
        }
        return JsonUtil.obj2String(msg);
    }


}
