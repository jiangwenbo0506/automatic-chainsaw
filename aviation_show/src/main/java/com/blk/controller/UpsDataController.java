package com.blk.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blk.common.R;
import com.blk.model.entity.UpsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.blk.service.UpsDataService;


import javax.annotation.Resource;

/**
 * ups信息
 *
 * @author Wilbur.J
 * @since 2024-11-11 17:42:19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/upsData")
public class UpsDataController {

    @Resource
    private UpsDataService upsDataService;

    /**
     * 获取Ups数据
     * @param deviceIdentifier
     * @return
     */
    @GetMapping("/getUpsByGatewayIdentifier")
    public R getUpsByGatewayIdentifier(@RequestParam String deviceIdentifier) {
        log.info("开始调用接口/upsData/getUpsByGatewayIdentifier------->>>>>> 请求参数: {}", deviceIdentifier);
        return R.ok(upsDataService.getOne(new LambdaQueryWrapper<UpsData>().eq(UpsData::getDeviceIdentifier, deviceIdentifier)));
    }


}

