package io.oicp.yorick61c.service;

import io.oicp.yorick61c.domain.body_exam.DataItem;

import java.util.List;

public interface BodyExamService {
    int saveDataItem(DataItem info);

    List<DataItem> getItemNameList();
}
