package com.blk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.mapper.ElectricDistributionInfoMapper;
import com.blk.model.entity.ElectricDistributionInfo;
import com.blk.model.vo.SwitchInfoVo;
import com.blk.service.ElectricDistributionInfoService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 配电房详情信息表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-10-30 16:02:27
 */
@Slf4j
@Service
public class ElectricDistributionInfoServiceImpl extends ServiceImpl<ElectricDistributionInfoMapper, ElectricDistributionInfo> implements ElectricDistributionInfoService {


    @Override
    public List<SwitchInfoVo> getSwitchNo(String stationName) {

        List<ElectricDistributionInfo> electricDistributionInfos = this.list(new QueryWrapper<ElectricDistributionInfo>().eq("station_name", stationName));
        List<SwitchInfoVo> result = electricDistributionInfos.stream()
                .map(info -> new SwitchInfoVo(info.getVoltageSwitchCode(), info.getTransformerName()))
                .collect(Collectors.toList());
        return result;
    }
}

