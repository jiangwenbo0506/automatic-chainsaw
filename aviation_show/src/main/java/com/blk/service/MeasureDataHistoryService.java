package com.blk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.MeasureDataHistory;
import com.blk.model.entity.MeasureDataLatest;

import java.text.ParseException;
import java.util.List;


/**
 * 珠海航展-低压开关实时数据表服务接口
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:27
 */
public interface MeasureDataHistoryService extends IService<MeasureDataHistory> {

    List<MeasureDataHistory> getHistoryCurve(String swicthNo,String stationName,String queryDate);

}

