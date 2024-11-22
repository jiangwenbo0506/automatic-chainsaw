package com.blk.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.model.entity.Warn;



/**
 * 珠海航展-告警列表服务接口
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
public interface WarnService extends IService<Warn> {

    void addByMeasureDataLatest(MeasureDataLatest MeasureDataLatest);

    void addByElectricOperationData(JSONObject properties);

}

