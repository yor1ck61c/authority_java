package io.oicp.yorick61c.service.impl;

import io.oicp.yorick61c.domain.DataItem;
import io.oicp.yorick61c.mapper.ExamDataItemMapper;
import io.oicp.yorick61c.service.BodyExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BodyExamServiceImpl implements BodyExamService {

    @Resource
    private ExamDataItemMapper dataItemMapper;

    @Override
    public int saveDataItem(DataItem info) {
        return dataItemMapper.insert(info);
    }
}
