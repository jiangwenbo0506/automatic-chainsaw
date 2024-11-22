package com.blk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.common.MeasureConstant;
import com.blk.mapper.ElectricOperationDataMapper;
import com.blk.model.entity.ElectricOperationData;
import com.blk.service.ElectricOperationDataService;
import com.blk.service.WarnService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * $tableInfo.comment.replaceAll("表","")表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-11-08 14:50:30
 */
@Slf4j
@Service("zhdhElectricOperationDataService")
public class ElectricOperationDataServiceImpl extends ServiceImpl<ElectricOperationDataMapper, ElectricOperationData> implements ElectricOperationDataService {

    @Resource
    private WarnService warnService;

    @Async
    @Override
    public void addHtsensor(String message) {
        // 解析 JSON 消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject properties = jsonObject.getJSONObject("properties");
        String tgHumidity = data.getString("tgHumidity");
        String tgTemperature = data.getString("tgTemperature");

        this.saveOrUpdate(new ElectricOperationData()
                .setTgHumidity(tgHumidity == null ? null : new BigDecimal(tgHumidity))
                .setTgTemperature(tgTemperature == null ? null : new BigDecimal(tgTemperature))
                .setGatewayIdentifier(properties.getString("gatewayIdentifier"))
                .setGatewayId(properties.getString("gatewayId"))
                .setDeviceIdentifier(properties.getString("deviceIdentifier"))
                .setDeviceId(properties.getString("deviceId"))
                .setServiceId(properties.getString("serviceId"))
                .setEventTime(new Date(properties.getDate("eventTime").getTime() - TimeUnit.HOURS.toMillis(8)))
                .setStationName(MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
                .setTgType("1"),
                new LambdaQueryWrapper<ElectricOperationData>()
                        .eq(ElectricOperationData::getGatewayId,properties.getString("gatewayId"))
                        .eq(ElectricOperationData::getStationName,MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
                        .eq(ElectricOperationData::getTgType,"1"));
    }
    @Async
    @Override
    public void addSmoke(String message) {
        // 解析 JSON 消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject properties = jsonObject.getJSONObject("properties");
        String tgStatus = data.getString("tgStatus");


        this.saveOrUpdate(new ElectricOperationData()
                .setTgStatus(tgStatus)
                .setGatewayIdentifier(properties.getString("gatewayIdentifier"))
                .setGatewayId(properties.getString("gatewayId"))
                .setDeviceIdentifier(properties.getString("deviceIdentifier"))
                .setDeviceId(properties.getString("deviceId"))
                .setServiceId(properties.getString("serviceId"))
                .setEventTime(new Date(properties.getDate("eventTime").getTime() - TimeUnit.HOURS.toMillis(8)))
                .setStationName(MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
                .setTgType("0"),
                new LambdaQueryWrapper<ElectricOperationData>()
                        .eq(ElectricOperationData::getGatewayId,properties.getString("gatewayId"))
                        .eq(ElectricOperationData::getStationName,MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
                        .eq(ElectricOperationData::getTgType,"0"));

        if ("1".equals(tgStatus)) {
            //新增告警数据
            warnService.addByElectricOperationData(properties);
        }
    }

    @Override
    public void addSmokeBatch(List<String> messageList) {
        List<ElectricOperationData> list = new ArrayList<>();

        for (String message : messageList) {
            // 解析 JSON 消息
            JSONObject jsonObject = JSONObject.parseObject(message);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject properties = jsonObject.getJSONObject("properties");
            String tgStatus = data.getString("tgStatus");

            list.add(new ElectricOperationData()
                    .setTgStatus(tgStatus)
                    .setGatewayIdentifier(properties.getString("gatewayIdentifier"))
                    .setGatewayId(properties.getString("gatewayId"))
                    .setDeviceIdentifier(properties.getString("deviceIdentifier"))
                    .setDeviceId(properties.getString("deviceId"))
                    .setServiceId(properties.getString("serviceId"))
                    .setEventTime(new Date(properties.getDate("eventTime").getTime() - TimeUnit.HOURS.toMillis(8)))
                    .setStationName(MeasureConstant.STATION_GATEWAY_ID_1.equals(properties.getString("gatewayId")) ? "1" : "2")
                    .setTgType("0"));
        }

        saveBatch(list);
    }


    @Override
    public List<ElectricOperationData> getByStationName(String stationName) {
        LambdaQueryWrapper<ElectricOperationData> smokeQueryWrapper = new LambdaQueryWrapper<>();
        smokeQueryWrapper.eq(ElectricOperationData::getStationName, stationName)
                .eq(ElectricOperationData::getTgType, "0")
                .orderByDesc(ElectricOperationData::getEventTime)
                .last("limit 1");
        ElectricOperationData smokeElectricOperationData = getOne(smokeQueryWrapper);


        LambdaQueryWrapper<ElectricOperationData> htsensorQueryWrapper = new LambdaQueryWrapper<>();
        htsensorQueryWrapper.eq(ElectricOperationData::getStationName, stationName)
                .eq(ElectricOperationData::getTgType, "1")
                .orderByDesc(ElectricOperationData::getEventTime)
                .last("limit 1");
        ElectricOperationData htsensorElectricOperationData = getOne(htsensorQueryWrapper);

        List<ElectricOperationData> result = new ArrayList<>();
        result.add(smokeElectricOperationData);
        result.add(htsensorElectricOperationData);

        return result;
    }
}

