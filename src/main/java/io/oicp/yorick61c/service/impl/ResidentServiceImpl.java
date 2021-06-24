package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.build_file.ResidentInfo;
import io.oicp.yorick61c.domain.login.User;
import io.oicp.yorick61c.mapper.body_exam.BuildFileOtherInfoMapper;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.mapper.ResidentBasicFileMapper;
import io.oicp.yorick61c.mapper.ResidentBuildFileMapper;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileInfoDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileOtherInfoDto;
import io.oicp.yorick61c.service.ResidentService;
import io.oicp.yorick61c.utils.MyStringUtil;
import io.oicp.yorick61c.utils.TimeUtil;
import io.oicp.yorick61c.utils.UserContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("ResidentService")
public class ResidentServiceImpl implements ResidentService {

    @Resource
    private ResidentBuildFileMapper residentBuildFileMapper;

    @Resource
    private ResidentBasicFileMapper residentBasicFileMapper;

    @Resource
    private BuildFileOtherInfoMapper buildFileOtherInfoMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    @Transactional
    public int insertResidentInfo(ResidentInfo residentInfo) {
        User user = userMapper.findUserByUsername(UserContext.getCurrentUserName());

        residentInfo.setUserId(user.getId()); //设置用户id
        residentInfo.setBuildFileTime(TimeUtil.getPresentFormatTimeString());
        residentInfo.setBuildFileResident(residentInfo.getResidentName());

        CResidentBuildFileInfoDto basicInfo = new CResidentBuildFileInfoDto();
        CResidentBuildFileOtherInfoDto otherInfo = new CResidentBuildFileOtherInfoDto();



        BeanUtils.copyProperties(residentInfo, basicInfo);
        BeanUtils.copyProperties(residentInfo, otherInfo);

        int s1 = residentBuildFileMapper.insert(basicInfo);
        int s2 = buildFileOtherInfoMapper.insert(reGenerateOtherInfo(residentInfo, otherInfo));

        return s1 == s2 && s1 == 1 ? 1 : 0;
    }

    private CResidentBuildFileOtherInfoDto reGenerateOtherInfo(ResidentInfo src, CResidentBuildFileOtherInfoDto dst) {
        try {
            dst.setMedicineAllergyHistoryList(MyStringUtil.StringArray2String(src.getMedicineAllergyHistoryList()));
            dst.setDiseaseHistoryList(MyStringUtil.StringArray2String(src.getDiseaseHistoryList()));
            dst.setExposedHistoryList(MyStringUtil.StringArray2String(src.getExposedHistoryList()));
            dst.setBrotherDiseaseHistoryList(MyStringUtil.StringArray2String(src.getBrotherDiseaseHistoryList()));
            dst.setChildrenDiseaseHistoryList(MyStringUtil.StringArray2String(src.getChildrenDiseaseHistoryList()));
            dst.setEnvironmentAllergyHistoryList(MyStringUtil.StringArray2String(src.getEnvironmentAllergyHistoryList()));
            dst.setExposedHistoryList(MyStringUtil.StringArray2String(src.getExposedHistoryList()));
            dst.setFatherDiseaseHistoryList(MyStringUtil.StringArray2String(src.getFatherDiseaseHistoryList()));
            dst.setMotherDiseaseHistoryList(MyStringUtil.StringArray2String(src.getMotherDiseaseHistoryList()));
            dst.setFoodAllergyHistoryList(MyStringUtil.StringArray2String(src.getFoodAllergyHistoryList()));

            return dst;
        } catch (NullPointerException e) {
            return dst;
        }

    }

    public List<CBasicFileTableDto> getResidentBasicFileTableInfo() {
        return residentBasicFileMapper.selectList(null);
    }

    @Override
    public int deleteResidentInfoById(Integer id) {

        int s1 = residentBuildFileMapper.deleteById(id);
        int s2 = buildFileOtherInfoMapper.deleteById(id);

        return s1 == s2 && s1 == 1? 1 : 0;
    }

    @Override
    public List<CBasicFileTableDto> getResidentNames() {
        return residentBasicFileMapper.selectList(new QueryWrapper<CBasicFileTableDto>().select("user_id", "resident_name"));
    }

    @Override
    public int insertResidentInfoFromExcel(List<ResidentInfo> residentInfo) {
        int sum = 0;
        for (ResidentInfo info: residentInfo) {
            sum += this.insertResidentInfo(info);
        }
        return sum == residentInfo.size() ? 1 : 0;
    }


}
