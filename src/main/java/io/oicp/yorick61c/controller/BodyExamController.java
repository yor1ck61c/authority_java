package io.oicp.yorick61c.controller;


import io.oicp.yorick61c.domain.body_exam.ExamDataItem;
import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.domain.body_exam.ExamItem;
import io.oicp.yorick61c.domain.body_exam.Examination;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CExamResultDto;
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
        return getReturnString(insertStatus);
    }

    @PostMapping("/save_exam_info")
    @ResponseBody
    public String saveExamInfo(@RequestBody ExamItem info) {
        int insertStatus = bodyExamService.saveExamInfo(info);
        return getReturnString(insertStatus);
    }
    @PostMapping("/save_excel_data")
    @ResponseBody
    public String saveExcelData(@RequestBody List<ExamDataItem> examDataItems) {
        int insertStatus = 0;
        for (ExamDataItem info : examDataItems){
            insertStatus = bodyExamService.saveDataItem(info);
            if (insertStatus == 0){break;}
        }
        return getMsgString(insertStatus);
    }

    private String getMsgString(int insertStatus) {
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

    @PostMapping("/save_examination")
    @ResponseBody
    public String saveExamination(@RequestBody Examination info) {
        System.out.println(info.toString());
        int insertStatus = bodyExamService.saveExamination(info);
        return getReturnString(insertStatus);
    }

    private String getReturnString(int insertStatus) {
        return getMsgString(insertStatus);
    }

    @GetMapping("/get_item_names")
    @ResponseBody
    public String getItemNameList() {
        List<ExamDataItem> nameList = bodyExamService.getItemNameList();
        return JsonUtil.obj2String(nameList);
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
}
