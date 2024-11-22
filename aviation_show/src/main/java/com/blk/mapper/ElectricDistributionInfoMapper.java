package com.blk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blk.model.vo.LedgerListVo;
import org.apache.ibatis.annotations.Mapper;
import com.blk.model.entity.ElectricDistributionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 配电房详情信息表数据库访问层
 *
 * @author Wilbur.J
 * @since 2024-10-30 16:02:28
 */
@Mapper
public interface ElectricDistributionInfoMapper extends BaseMapper<ElectricDistributionInfo> {


    List<LedgerListVo> getTransformerNames(@Param("stationName") String stationName);

}

