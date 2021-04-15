package io.oicp.yorick61c.service.impl;

import io.oicp.yorick61c.domain.User;
import io.oicp.yorick61c.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.dto.build_file_dto.CResidentBuildFileDto;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.ResidentBuildFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.service.ResidentService;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public int insertResidentInfo(CResidentBuildFileDto residentBuildFileDto) {
        User user = userMapper.findUserByUsername(UserContext.getCurrentUserName());
        residentBuildFileDto.setUserId(user.getId());
        residentBuildFileDto.setBuildFileResident(residentBuildFileDto.getResidentName());
        residentBuildFileDto.setBuildFileTime(new Date());
        return residentBuildFileMapper.insert(residentBuildFileDto);
    }

    public List<CBasicFileTableDto> getResidentBasicFileTableInfo() {
        return residentBasicFileMapper.selectList(null);
    }
}
