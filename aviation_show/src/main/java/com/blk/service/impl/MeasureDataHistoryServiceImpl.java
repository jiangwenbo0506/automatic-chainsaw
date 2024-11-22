package com.blk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blk.common.convert.MeasureDataLatestConvert;
import com.blk.mapper.MeasureDataHistoryMapper;
import com.blk.model.entity.MeasureDataHistory;
import com.blk.model.entity.MeasureDataLatest;
import com.blk.service.MeasureDataHistoryService;
import com.blk.service.MeasureDataLatestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 珠海航展-低压开关实时数据表服务实现类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:27
 */
@Slf4j
@Service
public class MeasureDataHistoryServiceImpl extends ServiceImpl<MeasureDataHistoryMapper, MeasureDataHistory> implements MeasureDataHistoryService {

    @Resource
    private MeasureDataLatestService measureDataLatestService;
    @Override
    public List<MeasureDataHistory> getHistoryCurve(String swicthNo,String stationName,String queryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(queryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("时间转换异常");
        }

        // 计算开始时间和结束时间
        long startOfDay = date.getTime();
        long endOfDay = startOfDay + (24 * 60 * 60 * 1000) - 1;

        return this.list(new QueryWrapper<MeasureDataHistory>().eq("swicth_no",swicthNo).eq("station_name",stationName).between("create_time",new Date(startOfDay), new Date(endOfDay)).orderByDesc("create_time"));
    }



    @Scheduled(cron = "0 0,15,30,45 * * * ?") // 每小时的第15分钟”执行一次任务
    public void syncMeasureDataLatest() {
        log.info("开始同步电流数据------>>>>>>>>");

        try {
            // 获取需要同步的数据
            List<MeasureDataLatest> list = measureDataLatestService.list();

            if (list.isEmpty()) {
                log.warn("实时电流数据为空！");
                return;
            }
            List<MeasureDataHistory> measureDataHistories = MeasureDataLatestConvert.INSTANCE.convertList(list);
            // 新增
            this.saveBatch(measureDataHistories);
        } catch (Exception e) {
            log.error("同步电流数据发生异常 --- >>>> {}", e);
            e.printStackTrace();
        }

        log.info("同步电流实时数据成功！");
    }


}

