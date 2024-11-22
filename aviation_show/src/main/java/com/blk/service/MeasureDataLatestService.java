package com.blk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.model.vo.LedgerListVo;
import com.blk.model.vo.Record;

import java.util.Date;
import java.util.List;


/**
 * 珠海航展-低压开关实时数据表服务接口
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:27
 */
public interface MeasureDataLatestService extends IService<MeasureDataLatest> {

    List<LedgerListVo> getList(String stationName);

    void add(List<Record> records, Date eventTime, String substationId);
}

