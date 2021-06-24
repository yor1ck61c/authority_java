package io.oicp.yorick61c.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.pojo.dto.build_file_dto.CResidentBuildFileInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResidentBuildFileMapper extends BaseMapper<CResidentBuildFileInfoDto> {
}
