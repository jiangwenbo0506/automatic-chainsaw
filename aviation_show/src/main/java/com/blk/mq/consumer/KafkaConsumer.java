
package com.blk.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.blk.common.MeasureConstant;
import com.blk.service.ElectricOperationDataService;
import com.blk.service.UpsDataService;
import com.blk.service.impl.RemoteFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaConsumer {

    @Resource
    private RemoteFileServiceImpl remoteFileService;

    @Resource
    private ElectricOperationDataService electricOperationDataService;

    @Resource
    private UpsDataService upsDataService;



    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @KafkaListener(topics = MeasureConstant.ELECTRIC_TOPIC, groupId = MeasureConstant.CONSUMER_GROUP, containerFactory = "batchContainerFactory")
    public void listenElectric(List<String> messages, Acknowledgment acknowledgment) {
        try {
            for (String message : messages) {
                // 异步处理每个消息
                executorService.submit(() -> electricProcessMessage(message));
            }
            // 手动确认消息
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理kafka消息时出错", e);
        }
    }

    @KafkaListener(topics = MeasureConstant.HTSENSOR_TOPIC, groupId = MeasureConstant.CONSUMER_GROUP, containerFactory = "batchContainerFactory")
    public void listenHtsensor(List<String> messages, Acknowledgment acknowledgment) {
        try {
            for (String message : messages) {
                // 异步处理每个消息
                executorService.submit(() -> htsensorProcessMessage(message));
            }
            // 手动确认消息
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理kafka消息时出错", e);
        }
    }

    @KafkaListener(topics = MeasureConstant.SMOKE_TOPIC, groupId = MeasureConstant.CONSUMER_GROUP, containerFactory = "batchContainerFactory")
    public void listenSmoke(List<String> messages, Acknowledgment acknowledgment) {
        try {
            for (String message : messages) {
                // 异步处理每个消息
                executorService.submit(() -> smokeProcessMessage(message));
            }
            // 手动确认消息
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理kafka消息时出错", e);
        }
    }

    @KafkaListener(topics = MeasureConstant.UPS_TOPIC, groupId = MeasureConstant.CONSUMER_GROUP, containerFactory = "batchContainerFactory")
    public void listenUPS(List<String> messages, Acknowledgment acknowledgment) {
        try {
            for (String message : messages) {
                // 异步处理每个消息
                executorService.submit(() -> upsProcessMessage(message));
            }
            // 手动确认消息
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("处理kafka消息时出错", e);
        }
    }

    private void electricProcessMessage(String message) {
        try {
            log.info("电流数据--->>> 获取到的kafka内容: {}", message);
            // 解析 JSON 消息
            JSONObject jsonObject = JSONObject.parseObject(message);
            JSONObject data = jsonObject.getJSONObject("data");
            String fileName = data.getString("tgAlarmName");

            // 调用远程文件服务获取文件
            remoteFileService.getRemoteFile(fileName);
        } catch (Exception e) {
            log.error("调用方法:processMessage发生错误 {}", message, e);
        }
    }

    private void htsensorProcessMessage(String message) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(message);
            String gatewayId = null;
            if (!jsonObject.isEmpty()){
                JSONObject properties = jsonObject.getJSONObject("properties");
                if(!properties.isEmpty()){
                    gatewayId = properties.getString("gatewayId");
                }
            }

            if(StringUtils.isNotBlank(gatewayId) && (MeasureConstant.STATION_GATEWAY_ID_1.equals(gatewayId) || MeasureConstant.STATION_GATEWAY_ID_2.equals(gatewayId))){
                // 保存
                electricOperationDataService.addHtsensor(message);
            }else {
                log.warn("解析到的数据所属的站点不存在，数据：{}",message);
            }


        } catch (Exception e) {
            log.error("调用方法:htsensorProcessMessage {}", message, e);
        }
    }

    private void smokeProcessMessage(String message) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(message);
            String gatewayId = null;
            if (!jsonObject.isEmpty()){
                JSONObject properties = jsonObject.getJSONObject("properties");
                if(!properties.isEmpty()){
                    gatewayId = properties.getString("gatewayId");
                }
            }

            if(StringUtils.isNotBlank(gatewayId) && (MeasureConstant.STATION_GATEWAY_ID_1.equals(gatewayId) || MeasureConstant.STATION_GATEWAY_ID_2.equals(gatewayId))){
                // 保存
                electricOperationDataService.addSmoke(message);
            }else {
                log.warn("解析到的数据所属的站点不存在，数据：{}",message);
            }

        } catch (Exception e) {
            log.error("调用方法:smokeProcessMessage {}", message, e);
        }
    }


    private void upsProcessMessage(String message) {

        try {

            JSONObject jsonObject = JSONObject.parseObject(message);
            String gatewayId = null;
            String serviceId = null;
            String deviceIdentifier = null;
            if (!jsonObject.isEmpty()){
                JSONObject properties = jsonObject.getJSONObject("properties");
                if(!properties.isEmpty()){
                    gatewayId = properties.getString("gatewayId");
                    serviceId = properties.getString("serviceId");
                    deviceIdentifier = properties.getString("deviceIdentifier");
                }
            }
            if(StringUtils.isNotBlank(gatewayId)
                && MeasureConstant.UPS_GATEWAY_ID_4.equals(gatewayId)
                    && StringUtils.isNotBlank(serviceId)
                        && (MeasureConstant.UPS_ELECTRIC_SERVICE_ID.equals(serviceId) || MeasureConstant.UPS_TEMPERATURE_SERVICE_ID.equals(serviceId))
                            && StringUtils.isNotBlank(deviceIdentifier)
                                && (MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_4_0.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_4_1.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_4_2.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_4_3.equals(deviceIdentifier))){
                log.info("ups电流数据--->>> 4条数据分支 --->>>> 获取到的kafka内容: {}", message);

                upsDataService.addUps(message);
            }

            if(StringUtils.isNotBlank(gatewayId)
                && MeasureConstant.UPS_GATEWAY_ID_8.equals(gatewayId)
                    && StringUtils.isNotBlank(serviceId)
                        && (MeasureConstant.UPS_ELECTRIC_SERVICE_ID.equals(serviceId) || MeasureConstant.UPS_TEMPERATURE_SERVICE_ID.equals(serviceId))
                            && StringUtils.isNotBlank(deviceIdentifier)
                                && (MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_0.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_1.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_2.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_3.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_4.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_5.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_6.equals(deviceIdentifier)
                                    || MeasureConstant.UPS_GATEWAY_DEVICEIDENTIFIER_8_7.equals(deviceIdentifier))){
                log.info("ups电流数据--->>> 8条数据分支 --->>>> 获取到的kafka内容: {}", message);
                upsDataService.addUps(message);
            }

        } catch (Exception e) {
            log.error("调用方法:upsProcessMessage {}", message, e);
        }
    }

    // 关闭线程池
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}