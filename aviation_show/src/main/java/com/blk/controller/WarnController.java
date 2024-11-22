package com.blk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blk.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.blk.model.entity.Warn;
import com.blk.service.WarnService;


import javax.annotation.Resource;
import java.util.Map;

/**
 * 告警信息
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/warn")
public class WarnController {

    @Resource
    private WarnService warnService;

    /**
     * 获取告警信息分页列表
     * @param params
     * @return
     */

    @GetMapping("/page")
    public R<Page<Warn>> page(@RequestParam Map<String, Object> params){
        log.info("开始调用接口/warn/page接口------->>>>>> 请求参数: {}", params);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("station_name",params.get("stationName"));
        return R.ok(warnService.page(new Page<>(Long.parseLong(params.get("current").toString()), Long.parseLong(params.get("size").toString())),queryWrapper));
    }
    
}

