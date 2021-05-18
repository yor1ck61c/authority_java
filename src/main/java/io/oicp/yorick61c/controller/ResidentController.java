package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileDto;
import io.oicp.yorick61c.service.ResidentService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/resident")
public class ResidentController {

    @Resource
    private ResidentService residentService;

    @PostMapping("/saveInfo")
    public void saveResidentInfo(@RequestBody CResidentBuildFileDto rbfDto) {
        residentService.insertResidentInfo(rbfDto);
        //System.out.println(rbfDto);
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public String getResidentInfo(){

        return JsonUtil.obj2String(residentService.getResidentBasicFileTableInfo());
    }

    @PostMapping("/deleteInfo")
    public void deleteResidentInfo(@RequestBody Integer userId) {
        if (userId != null) {
            residentService.deleteResidentInfoById(userId);
        } else {
            System.out.println("id is null!");
        }
    }


}
