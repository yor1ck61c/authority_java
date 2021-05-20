package io.oicp.yorick61c.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.domain.body_exam.DataItem;
import io.oicp.yorick61c.mapper.ExamDataItemMapper;
import io.oicp.yorick61c.service.BodyExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BodyExamServiceImpl implements BodyExamService {

    @Resource
    private ExamDataItemMapper dataItemMapper;

    @Override
    public int saveDataItem(DataItem info) {
        return dataItemMapper.insert(info);
    }

    @Override
    public List<DataItem> getItemNameList() {
        return dataItemMapper.selectList(new QueryWrapper<DataItem>().select("item_id","item_name"));
    }
}
