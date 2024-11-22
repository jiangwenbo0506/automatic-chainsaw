package com.blk.controller;


import com.blk.common.R;
import com.blk.service.ElectricOperationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 电房温湿度信息
 *
 * @author Wilbur.J
 * @since 2024-11-08 14:50:29
 */

@Slf4j
@RestController
@RequestMapping("/electricOperationData")
public class ElectricOperationDataController {

    @Resource
    private ElectricOperationDataService electricOperationDataService;

    /**
     * 获取配电房温湿度信息
     * @param stationName
     * @return
     */
    @GetMapping("/getByStationName")
    public R getByStationName(@RequestParam String stationName) {
        log.info("开始调用接口/electricOperationData/getByStationName------->>>>>> 请求参数: {}", stationName);
        return R.ok(electricOperationDataService.getByStationName(stationName));
    }

}

