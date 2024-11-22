package com.blk.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LedgerListVo {
    /**
     * 公变名称
     */
    private String transformerName;
    /**
     * 变压器容量
     */
    private Integer transformerCapacity;
    /**
     * 低压分支开关编号
     */
    private List<VoltageSwitchVo> voltageSwitchCode;

}
