package io.oicp.yorick61c.controller;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.service.PrivilegeService;
import io.oicp.yorick61c.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Resource(name = "PrivilegeService")
    private PrivilegeService privilegeService;

    @GetMapping("/account")
    @ResponseBody
    public String getAccountInfo() {

        List<User> accountInfo = privilegeService.getAccountInfo();
        return JsonUtil.obj2String(accountInfo);
    }

    @PostMapping("/save")
    // @RequestBody用来接收前端传递给后端的json字符串中的数据的(请求体中的数据
    public void saveAccount(@RequestBody User user) {
        //System.out.println(user.getRole());
        privilegeService.save(user);
    }

    @PostMapping("/update")
    public void updateAccount(@RequestBody User user) {
        privilegeService.update(user);
    }

    @PostMapping("/delete")
    public void deleteAccount(@RequestBody User user) {
        privilegeService.delete(user.getId());
    }

}
