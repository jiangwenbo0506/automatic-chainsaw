package com.blk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blk.model.entity.ElectricDistributionInfo;
import com.blk.model.vo.SwitchInfoVo;

import java.util.List;

/**
 * 配电房详情信息表服务接口
 *
 * @author Wilbur.J
 * @since 2024-10-30 16:02:27
 */
public interface ElectricDistributionInfoService extends IService<ElectricDistributionInfo> {


    List<SwitchInfoVo> getSwitchNo(String stationName);
}

