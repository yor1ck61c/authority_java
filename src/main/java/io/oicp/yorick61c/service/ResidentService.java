package io.oicp.yorick61c.service;

import io.oicp.yorick61c.pojo.dto.build_file_dto.CBasicFileTableDto;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileDto;

import java.util.List;

public interface ResidentService {

    void insertResidentInfo(CResidentBuildFileDto residentBuildFileDto);

    List<CBasicFileTableDto> getResidentBasicFileTableInfo();

    void deleteResidentInfoById(Integer id);
}
