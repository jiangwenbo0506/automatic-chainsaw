package com.blk.model.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SwitchInfoVo {


    /**
     * 低压分支开关编号
     */
    private String voltageSwitchCode;

    /**
     * 公变名称
     */
    private String transformerName;

    public SwitchInfoVo(String voltageSwitchCode, String transformerName) {
        this.voltageSwitchCode = voltageSwitchCode;
        this.transformerName = transformerName;
    }
}
