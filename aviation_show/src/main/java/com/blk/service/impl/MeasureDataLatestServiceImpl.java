package com.blk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.mapper.ElectricDistributionInfoMapper;
import com.blk.mapper.MeasureDataLatestMapper;
import com.blk.model.entity.ElectricDistributionInfo;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.model.vo.LedgerListVo;
import com.blk.model.vo.MeasureDataVo;
import com.blk.model.vo.Record;
import com.blk.model.vo.VoltageSwitchVo;
import com.blk.service.ElectricDistributionInfoService;
import com.blk.service.MeasureDataLatestService;
import com.blk.service.WarnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


/**
 * 珠海航展-低压开关实时数据表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:27
 */
@Slf4j
@Service
public class MeasureDataLatestServiceImpl extends ServiceImpl<MeasureDataLatestMapper, MeasureDataLatest> implements MeasureDataLatestService {

    @Resource
    private ElectricDistributionInfoService electricDistributionInfoService;

    @Resource
    private ElectricDistributionInfoMapper electricDistributionInfoMapper;

    @Resource
    private WarnService warnService;

    private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();


    @Override
    public List<LedgerListVo> getList(String stationName) {

        List<LedgerListVo> resultList = electricDistributionInfoMapper.getTransformerNames(stationName);

        //TODO 缺少低压总开数据
        for (LedgerListVo ledgerListVo : resultList) {
            List<ElectricDistributionInfo> list = electricDistributionInfoService.list(new QueryWrapper<ElectricDistributionInfo>().eq("transformer_name", ledgerListVo.getTransformerName()).eq("station_name", stationName));
            List<VoltageSwitchVo> voltageSwitchVoList = new ArrayList<>();
            for (ElectricDistributionInfo electricDistributionInfo : list) {
                List<MeasureDataLatest> swicthList = this.list(new QueryWrapper<MeasureDataLatest>().eq("station_name",stationName).eq("swicth_no", electricDistributionInfo.getVoltageSwitchCode()));
                List<MeasureDataVo> currentData = new ArrayList<>();
                for (MeasureDataLatest measureDataLatest : swicthList) {
                    currentData.add(new MeasureDataVo()
                            .setMonitoringType("A相电流")
                            .setMonitoringData(measureDataLatest.getTgia() == null ? null : measureDataLatest.getTgia().toString())
                            .setEventTime(measureDataLatest.getEventTime()));
                    currentData.add(new MeasureDataVo()
                            .setMonitoringType("B相电流")
                            .setMonitoringData(measureDataLatest.getTgib() == null ? null : measureDataLatest.getTgib().toString())
                            .setEventTime(measureDataLatest.getEventTime()));
                    currentData.add(new MeasureDataVo()
                            .setMonitoringType("C相电流")
                            .setMonitoringData(measureDataLatest.getTgic() == null ? null : measureDataLatest.getTgic().toString())
                            .setEventTime(measureDataLatest.getEventTime()));
                    currentData.add(new MeasureDataVo()
                            .setMonitoringType("开关状态")
                            .setMonitoringData(measureDataLatest.getSwicthStatus()));
                }

                voltageSwitchVoList.add(new VoltageSwitchVo()
                        .setVoltageSwitchCode(electricDistributionInfo.getVoltageSwitchCode())
                        .setRated_current(electricDistributionInfo.getRatedCurrent())
                        .setMountType(electricDistributionInfo.getMountType())
                        .setCurrentData(currentData));
            }
            ledgerListVo.setVoltageSwitchCode(voltageSwitchVoList);
        }
        return resultList;
    }

    @Async
    @Override
    public void add(List<Record> records, Date eventTime, String substationId) {
        // 批量查询 ElectricDistributionInfo
        List<String> tabList = records.stream().map(Record::getTab).distinct().collect(Collectors.toList());
        List<ElectricDistributionInfo> electricDistributionInfos = electricDistributionInfoService.list(new QueryWrapper<ElectricDistributionInfo>()
                .eq("station_name", substationId)
                .in("voltage_switch_code", tabList));

        // 创建缓存 Map --减少数据库开销
        Map<String, ElectricDistributionInfo> infoMap = electricDistributionInfos.stream()
                .collect(Collectors.toMap(ElectricDistributionInfo::getVoltageSwitchCode, info -> info));

        // 处理记录并准备批量保存或更新
        List<MeasureDataLatest> toSaveOrUpdate = records.stream().map(record -> {
            ElectricDistributionInfo info = infoMap.get(record.getTab());
            if (info != null && (StringUtils.isNotBlank(record.getValue1()) && StringUtils.isNotBlank(record.getValue2()) && StringUtils.isNotBlank(record.getValue3()))) {

                return new MeasureDataLatest()
                        .setTgia((record.getValue1() == null || "".equals(record.getValue1())) ? null : new BigDecimal(record.getValue1()))
                        .setTgib((record.getValue2() == null || "".equals(record.getValue2())) ? null : new BigDecimal(record.getValue2()))
                        .setTgic((record.getValue3() == null || "".equals(record.getValue3())) ? null : new BigDecimal(record.getValue3()))
                        .setSwicthStatus(record.getState())
                        .setSwicthNo(record.getTab())
                        .setEventTime(eventTime)
                        .setTransformName(info.getTransformerName())
                        .setTransformCapacity(info.getTransformerCapacity().toString())
                        .setStationName(substationId);

            } else {
                log.warn("调用文件解析获取到的电流数据为空----->>> 所属配电站：{}， 数据：{}",substationId,record.toString());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());


        // 处理保存或更新
        toSaveOrUpdate.forEach(data -> {
            ReentrantLock recordLock = locks.computeIfAbsent(data.getSwicthNo(), k -> new ReentrantLock());
            recordLock.lock();
            try {
                MeasureDataLatest existingData = this.getOne(new QueryWrapper<MeasureDataLatest>()
                        .eq("swicth_no", data.getSwicthNo())
                        .eq("station_name", data.getStationName()));

                if (existingData != null) {
                    // 检查 swicth_status 是否发生变化
                    if (!existingData.getSwicthStatus().equals(data.getSwicthStatus())) {
                        // 写入告警数据
                        warnService.addByMeasureDataLatest(data);
                    }
                }
                // 新增或更新
                try {
                    this.saveOrUpdate(data, new QueryWrapper<MeasureDataLatest>()
                            .eq("swicth_no", data.getSwicthNo())
                            .eq("station_name", data.getStationName()));
                } catch (DuplicateKeyException e) {
                    log.warn("已经存在重复的 开关编号+配电站名称 ---->>> swicth_no: {} and station_name: {}. Data not inserted or updated.", data.getSwicthNo(), data.getStationName(), e);
                }
            } finally {
                recordLock.unlock();
            }
        });
    }
}



