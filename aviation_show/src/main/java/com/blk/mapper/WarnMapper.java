package com.blk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.blk.model.entity.Warn;


/**
 * 珠海航展-告警列表数据库访问层
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Mapper
public interface WarnMapper extends BaseMapper<Warn> {


}

