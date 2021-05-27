package io.oicp.yorick61c.controller;


import io.oicp.yorick61c.domain.body_exam.DataItem;
import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.pojo.dto.body_exam_dto.CItemNameDto;
import io.oicp.yorick61c.service.BodyExamService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exam")
public class BodyExamController {

    @Resource(description = "bodyExamService")
    private BodyExamService bodyExamService;

    @PostMapping("/save_data_item")
    @ResponseBody
    public String saveExamDataItemInfo(@RequestBody DataItem info) {
        int insertStatus = bodyExamService.saveDataItem(info);
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

    @PostMapping("/save_excel_item")
    @ResponseBody
    public String saveExamExcelItemInfo(@RequestBody List<DataItem> ItemInfo) {
        int insertStatus = 0;
        for (DataItem info : ItemInfo){
            insertStatus = bodyExamService.saveDataItem(info);
            if (insertStatus == 0){break;}
        }
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

    @GetMapping("/get_item_names")
    @ResponseBody
    public String getItemNameList() {
        List<DataItem> nameList = bodyExamService.getItemNameList();
        return JsonUtil.obj2String(nameList);
    }
}
