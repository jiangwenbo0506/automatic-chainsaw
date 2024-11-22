package com.blk.controller;


import com.blk.common.R;
import com.blk.service.MeasureDataHistoryService;
import com.blk.service.MeasureDataLatestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  开关电流数据
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/measureData")
public class MeasureDataController {

    @Resource
    private MeasureDataLatestService measureDataLatestService;
    @Resource
    private MeasureDataHistoryService measureDataHistoryService;

    /**
     * 获取详情页台账列表
     * @param stationName
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam String stationName){
        log.info("开始调用接口/measureData/list------->>>>>> 请求参数: {}", stationName);
        return R.ok(measureDataLatestService.getList(stationName));
    }


    /**
     * 获取电流曲线数据
     * @param swicthNo
     * @return
     */
    @GetMapping("/current/getCurve")
    public R getCurve(@RequestParam String swicthNo, String stationName,String queryDate){
        log.info("开始调用接口/measureData/current/getCurve------->>>>>> 请求参数: {}", swicthNo +"|"+ stationName + "|" + queryDate);
        return R.ok(measureDataHistoryService.getHistoryCurve(swicthNo,stationName,queryDate));
    }

    
}

