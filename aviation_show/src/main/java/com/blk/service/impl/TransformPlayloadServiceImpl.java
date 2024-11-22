package com.blk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.mapper.TransformPlayloadMapper;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.model.entity.TransformPlayload;
import com.blk.service.MeasureDataLatestService;
import com.blk.service.TransformPlayloadService;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/**
 * 珠海航展-负载率曲线列表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:28
 */
@Slf4j
@Service("TransformPlayloadService")
public class TransformPlayloadServiceImpl extends ServiceImpl<TransformPlayloadMapper, TransformPlayload> implements TransformPlayloadService {


    @Resource
    private MeasureDataLatestService measureDataLatestService;

    //@Scheduled(cron = "0 */15 * * * ?") // 每15分钟执行一次
    public void scheduledAdd() {

        List<MeasureDataLatest> list = measureDataLatestService.list();
        List<TransformPlayload> saveList = new ArrayList<>();
        //负载率 = 三相中最大一相电流➗线路额定电流
        for (MeasureDataLatest measureDataLatest : list) {
            // 获取三个值中的最大值
            BigDecimal max = maxOfThree(measureDataLatest.getTgia(), measureDataLatest.getTgib(), measureDataLatest.getTgic());
            BigDecimal divide = max.divide(new BigDecimal(measureDataLatest.getTransformCapacity()), 2, RoundingMode.HALF_UP);

            saveList.add(new TransformPlayload()
                    .setTransformPlayload(divide)
                    .setTransformName(measureDataLatest.getTransformName())
                    .setStationName(measureDataLatest.getStationName())
                    .setTransformCapacity(measureDataLatest.getTransformCapacity()));
        }

        this.saveBatch(saveList);
    }

    private BigDecimal maxOfThree(BigDecimal a, BigDecimal b, BigDecimal c) {
        return a.max(b).max(c);
    }
}

