package com.blk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.common.MeasureConstant;
import com.blk.mapper.UpsDataMapper;
import com.blk.model.entity.UpsData;
import com.blk.service.UpsDataService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * $tableInfo.comment.replaceAll("表","")表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-11-11 17:42:19
 */
@Slf4j
@Service("upsDataService")
public class UpsDataServiceImpl extends ServiceImpl<UpsDataMapper, UpsData> implements UpsDataService {

    @Async
    @Override
    public void addUps(String message) {
        // 解析 JSON 消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject properties = jsonObject.getJSONObject("properties");

        this.saveOrUpdate(new UpsData()
                        .setTgIa(data.getString("tgIa") == null ? null : new BigDecimal(data.getString("tgIa")))
                        .setTgIb(data.getString("tgIb") == null ? null : new BigDecimal(data.getString("tgIb")))
                        .setTgIc(data.getString("tgIc") == null ? null : new BigDecimal(data.getString("tgIc")))
                        .setTgTemp01(data.getString("tgTemp01") == null ? null : new BigDecimal(data.getString("tgTemp01")))
                        .setTgTemp02(data.getString("tgTemp02") == null ? null : new BigDecimal(data.getString("tgTemp02")))
                        .setTgTemp03(data.getString("tgTemp03") == null ? null : new BigDecimal(data.getString("tgTemp03")))
                        .setGatewayIdentifier(properties.getString("gatewayIdentifier"))
                        .setGatewayId(properties.getString("gatewayId"))
                        .setDeviceIdentifier(properties.getString("deviceIdentifier"))
                        .setDeviceId(properties.getString("deviceId"))
                        .setServiceId(properties.getString("serviceId"))
                        .setEventTime(new Date(properties.getDate("eventTime").getTime() - TimeUnit.HOURS.toMillis(8))),
                new LambdaQueryWrapper<UpsData>()
                        .eq(UpsData::getGatewayId,properties.getString("gatewayId"))
                        .and(i -> i.eq(UpsData::getServiceId, MeasureConstant.UPS_ELECTRIC_SERVICE_ID).or().eq(UpsData::getServiceId, MeasureConstant.UPS_TEMPERATURE_SERVICE_ID))
                        .eq(UpsData::getDeviceIdentifier,properties.getString("deviceIdentifier")));
    }

}

