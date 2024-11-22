package com.blk.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * $tableInfo.comment.replaceAll("表","")表实体类
 *
 * @author Wilbur.J
 * @since 2024-11-08 14:50:31
 */
@Data
@Accessors(chain = true)
@TableName("zhdh_electric_operation_data")
public class ElectricOperationData {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 烟感状态 0：正常 1：报警
     */
    @TableField("tg_status")
    private String tgStatus;

    /**
     * 湿度
     */
    @TableField("tg_humidity")
    private BigDecimal tgHumidity;

    /**
     * 温度
     */
    @TableField("tg_temperature")
    private BigDecimal tgTemperature;

    /**
     * 网关设备身份标识
     */
    @TableField("gateway_identifier")
    private String gatewayIdentifier;

    /**
     * 网关id
     */
    @TableField("gateway_id")
    private String gatewayId;

    /**
     * 设备身份标识
     */
    @TableField("device_identifier")
    private String deviceIdentifier;

    /**
     * 设备id
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 服务id
     */
    @TableField("service_id")
    private String serviceId;

    /**
     * 事件时间
     */
    @TableField("event_time")
    private Date eventTime;

    /**
     * 创建人id
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 修改人id
     */
    @TableField("updator_id")
    private String updatorId;
    /**
     * 所在站房名称
     */
    @TableField("station_name")
    private String stationName;

    /**
     * 类型：0：烟感；1：温湿度
     */
    @TableField("tg_type")
    private String tgType;



}

