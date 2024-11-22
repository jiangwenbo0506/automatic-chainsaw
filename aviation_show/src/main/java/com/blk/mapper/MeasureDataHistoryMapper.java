package com.blk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.blk.model.entity.MeasureDataHistory;


/**
 * 珠海航展-低压开关历史数据表数据库访问层
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:26
 */
@Mapper
public interface MeasureDataHistoryMapper extends BaseMapper<MeasureDataHistory> {


}

