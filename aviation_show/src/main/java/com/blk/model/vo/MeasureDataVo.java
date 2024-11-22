package com.blk.model.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class MeasureDataVo {

    /**
     * 监测类型
     */
    private String monitoringType;
    /**
     * 监测数据
     */
    private String monitoringData;
    /**
     * 监测时间
     */
    private Date eventTime;

}
