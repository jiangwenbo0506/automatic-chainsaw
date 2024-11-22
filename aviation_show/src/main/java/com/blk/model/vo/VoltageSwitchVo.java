package com.blk.model.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class VoltageSwitchVo {

    /**
     * 低压分支开关编号
     */
    private String voltageSwitchCode;

    /**
     * 额定电流
     */
    private Integer rated_current;
    /**
     * 挂载负荷类型
     */
    private String mountType;
    /**
     * 电流数据
     */
    private List<MeasureDataVo> currentData;
}
