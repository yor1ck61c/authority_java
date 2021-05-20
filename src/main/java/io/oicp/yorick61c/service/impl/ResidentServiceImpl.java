package io.oicp.yorick61c.service.impl;

import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileDto;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.ResidentBuildFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.ResidentService;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("ResidentService")
public class ResidentServiceImpl implements ResidentService {

    @Resource
    private ResidentBuildFileMapper residentBuildFileMapper;

    @Resource
    private ResidentBasicFileMapper residentBasicFileMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public void insertResidentInfo(CResidentBuildFileDto residentBuildFileDto) {
        User user = userMapper.findUserByUsername(UserContext.getCurrentUserName());

        residentBuildFileDto.setUserId(user.getId()); //设置用户id
        residentBuildFileDto.setBuildFileResident(residentBuildFileDto.getResidentName()); //设置建档人

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        residentBuildFileDto.setBuildFileTime(sdf.format(new Date()));
        residentBuildFileMapper.insert(residentBuildFileDto);
    }

    public List<CBasicFileTableDto> getResidentBasicFileTableInfo() {
        return residentBasicFileMapper.selectList(null);
    }

    @Override
    public void deleteResidentInfoById(Integer id) {
        residentBuildFileMapper.deleteById(id);
    }
}
