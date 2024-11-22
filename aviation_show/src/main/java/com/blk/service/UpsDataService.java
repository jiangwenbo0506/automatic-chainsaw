package com.blk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.UpsData;


/**
 * $tableInfo.comment.replaceAll("表","")表服务接口
 *
 * @author Wilbur.J
 * @since 2024-11-11 17:42:19
 */
public interface UpsDataService extends IService<UpsData> {

    void addUps(String message);


}

