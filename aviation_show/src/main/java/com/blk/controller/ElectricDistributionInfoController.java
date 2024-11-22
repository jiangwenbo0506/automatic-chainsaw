package com.blk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blk.common.R;
import com.blk.model.entity.ElectricDistributionInfo;
import com.blk.service.ElectricDistributionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 配电房详情信息表控制层
 *
 * @author Wilbur.J
 * @since 2024-10-30 16:25:27
 */
@Slf4j
@RestController
@RequestMapping("/electricDistribution")
public class ElectricDistributionInfoController  {


    @Resource
    private ElectricDistributionInfoService electricDistributionInfoService;


    /**
     * 通过电房名称查询
     *
     * @param stationName 主键
     * @return 集合
     */
    @GetMapping("/info")
    public R selectOne(@RequestParam String stationName) {
        log.info("开始调用接口/electricDistribution/info------->>>>>> 请求参数: {}", stationName);
        return R.ok(electricDistributionInfoService.list(new QueryWrapper<ElectricDistributionInfo>().eq("station_name",stationName)));
    }

    /**
     * 查询开关
     *
     * @param stationName 电房id
     * @return 集合
     */
    @GetMapping("/getSwitchNo")
    public R getSwitchNo(@RequestParam String stationName) {
        log.info("开始调用接口/electricDistribution/getSwitchNo------->>>>>> 请求参数: {}", stationName);
        return R.ok(this.electricDistributionInfoService.getSwitchNo(stationName));
    }


}

