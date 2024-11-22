package com.blk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.common.MeasureConstant;
import com.blk.mapper.WarnMapper;
import com.blk.model.entity.ElectricDistributionInfo;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.model.entity.Warn;
import com.blk.service.ElectricDistributionInfoService;
import com.blk.service.WarnService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;


/**
 * 珠海航展-告警列表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Slf4j
@Service("WarnService")
public class WarnServiceImpl extends ServiceImpl<WarnMapper, Warn> implements WarnService {

    @Resource
    private ElectricDistributionInfoService electricDistributionInfoService;

    @Override
    public void addByMeasureDataLatest(MeasureDataLatest measureDataLatest) {
        log.info("新增告警数据：{}", measureDataLatest.toString());

        ElectricDistributionInfo electricDistributionInfo = electricDistributionInfoService.getOne(new QueryWrapper<ElectricDistributionInfo>().eq("station_name", measureDataLatest.getStationName()).eq("voltage_switch_code", measureDataLatest.getSwicthNo()));
        this.save(new Warn()
                .setContent("开关变位警告")
                .setDevice(measureDataLatest.getSwicthNo())
                .setEventTime(measureDataLatest.getEventTime())
                .setTransformName(electricDistributionInfo.getTransformerName())
                .setSwitchNo(measureDataLatest.getSwicthNo())
                .setStationName(measureDataLatest.getStationName())
        );
    }

    @Override
    public void addByElectricOperationData(JSONObject properties) {
        log.info("新增告警数据：{}", properties.toString());
        this.save(new Warn()
                .setContent("烟雾告警")
                .setDevice("烟感")
                .setEventTime(properties.getDate("eventTime"))
                .setGatewayIdentifier(properties.getString("gatewayIdentifier"))
                .setTransformName(MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "航展馆#1配电站" : "航展馆#2配电站")
                .setStationName(MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
        );


    }

}

