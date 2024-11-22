package com.blk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.ElectricOperationData;

import java.util.List;


/**
 * $tableInfo.comment.replaceAll("表","")表服务接口
 *
 * @author Wilbur.J
 * @since 2024-11-08 14:50:30
 */
public interface ElectricOperationDataService extends IService<ElectricOperationData> {


    void addHtsensor(String message);

    void addSmoke(String message);

    void addSmokeBatch(List<String> messageList);

    List<ElectricOperationData> getByStationName(String stationName);


}

