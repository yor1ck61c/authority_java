package io.oicp.yorick61c.controller;


import io.oicp.yorick61c.domain.DataItem;
import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.service.BodyExamService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
}
