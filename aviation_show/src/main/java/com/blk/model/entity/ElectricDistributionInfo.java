package com.blk.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配电房详情信息表实体类
 *
 * @author Wilbur.J
 * @since 2024-10-30 16:02:28
 */
@Data
@Accessors(chain = true)
@TableName("zhdh_electric_distribution_info")
public class ElectricDistributionInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 供电所
     */
    @TableField("electric_station")
    private String electricStation;

    /**
     * 所属变电站
     */
    @TableField("electric_station_source")
    private String electricStationSource;

    /**
     * 所属线路
     */
    @TableField("electric_station_line")
    private String electricStationLine;

    /**
     * 公变名称
     */
    @TableField("transformer_name")
    private String transformerName;

    /**
     * 变压器容量
     */
    @TableField("transformer_capacity")
    private Integer transformerCapacity;

    /**
     * 变压器型号
     */
    @TableField("transformer_type")
    private String transformerType;

    /**
     * 变压器厂家
     */
    @TableField("manufactor")
    private String manufactor;

    /**
     * 低压开关编号
     */
    @TableField("voltage_switch_code")
    private String voltageSwitchCode;

    /**
     * 挂载负荷类型
     */
    @TableField("mount_type")
    private String mountType;

    /**
     * 额定电流
     */
    @TableField("rated_current")
    private Integer ratedCurrent;

    /**
     * 出线线经
     */
    @TableField("out_diameter")
    private String outDiameter;

    /**
     * 用户编号
     */
    @TableField("elec_cust_no")
    private String elecCustNo;

    /**
     * 关联文件解析后的电流数据
     */
    @TableField("station_name")
    private String stationName;


}

