package com.blk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blk.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.blk.model.entity.TransformPlayload;
import com.blk.service.TransformPlayloadService;


import javax.annotation.Resource;
import java.util.List;

/**
 * 负载率曲线
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/playload")
public class TransformPlayloadController {

    @Resource
    private TransformPlayloadService transformPlayloadService;


    /**
     * 查询所有数据
     *
     * @param stationName 过滤条件
     * @return 所有数据
     */
    @GetMapping("/getCurve")
    public R<List<TransformPlayload>> list(@RequestParam String stationName) {
        log.info("开始调用接口/playload/getCurve------->>>>>> 请求参数: {}", stationName);
        return R.ok(this.transformPlayloadService.list(new QueryWrapper<TransformPlayload>().eq("station_name", stationName)));
    }

    
}

