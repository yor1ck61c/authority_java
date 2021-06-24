package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.domain.MsgBox;
import io.oicp.yorick61c.domain.build_file.ResidentInfo;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileInfoDto;
import io.oicp.yorick61c.service.ResidentService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/resident")
public class ResidentController {

    @Resource
    private ResidentService residentService;

    @PostMapping("/save_info")
    @ResponseBody
    public String saveResidentInfo(@RequestBody ResidentInfo residentInfo) {
        int res = residentService.insertResidentInfo(residentInfo);
        return this.getBuildFileMsgString(res);
    }

    @PostMapping("/save_info_from_excel")
    @ResponseBody
    public String saveResidentInfoFromExcel(@RequestBody List<ResidentInfo> residentInfo) {
        int res = residentService.insertResidentInfoFromExcel(residentInfo);
        return this.getBuildFileMsgString(res);
    }

    @GetMapping("/get_info")
    @ResponseBody
    public String getResidentInfo(){

        return JsonUtil.obj2String(residentService.getResidentBasicFileTableInfo());
    }

    @GetMapping("/delete_info")
    @ResponseBody
    public String deleteResidentInfo(Integer userId) {
        if (userId != null) {
            int deleteStatus = residentService.deleteResidentInfoById(userId);
            return getDeleteMsgString(deleteStatus);
        } else {
            MsgBox msgBox = new MsgBox();
            msgBox.setMsg("用户id为空，无法删除");
            msgBox.setCode(0);
            return JsonUtil.obj2String(msgBox);
        }
    }

    @GetMapping("/get_resident_names")
    @ResponseBody
    public String getResidentNames(){
        return JsonUtil.obj2String(residentService.getResidentNames());
    }


    private String getBuildFileMsgString(int insertStatus) {
        MsgBox msg = new MsgBox();
        if (insertStatus == 0) {
            msg.setCode(0);
            msg.setMsg("建档失败");
        } else {
            msg.setCode(1);
            msg.setMsg("建档成功");
        }
        return JsonUtil.obj2String(msg);
    }

    private String getDeleteMsgString(int deleteStatus) {
        MsgBox msg = new MsgBox();
        if (deleteStatus == 0) {
            msg.setCode(0);
            msg.setMsg("删除失败");
        } else {
            msg.setCode(1);
            msg.setMsg("删除成功");
        }
        return JsonUtil.obj2String(msg);
    }

}
