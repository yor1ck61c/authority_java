package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.build_file.ResidentInfo;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileInfoDto;

import java.util.List;

public interface ResidentService {

    int insertResidentInfo(ResidentInfo residentInfo);

    List<CBasicFileTableDto> getResidentBasicFileTableInfo();

    int deleteResidentInfoById(Integer id);

    List<CBasicFileTableDto> getResidentNames();

    int insertResidentInfoFromExcel(List<ResidentInfo> residentInfo);
}
