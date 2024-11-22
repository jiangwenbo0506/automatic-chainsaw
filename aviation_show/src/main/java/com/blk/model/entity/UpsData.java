package com.blk.model.entity;



import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * $tableInfo.comment.replaceAll("表","")表实体类
 *
 * @author Wilbur.J
 * @since 2024-11-11 17:42:20
 */
@Data
@Accessors(chain = true)
@TableName("zhdh_ups_data")
public class UpsData {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 电流a
     */
    @TableField("tg_ia")
    private BigDecimal tgIa;

    /**
     * 电流b
     */
    @TableField("tg_ib")
    private BigDecimal tgIb;

    /**
     * 电流c
     */
    @TableField("tg_ic")
    private BigDecimal tgIc;
    /**
     * 温度01
     */
    @TableField("tg_temp01")
    private BigDecimal tgTemp01;
    /**
     * 温度02
     */
    @TableField("tg_temp02")
    private BigDecimal tgTemp02;
    /**
     * 温度03
     */
    @TableField("tg_temp03")
    private BigDecimal tgTemp03;
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
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人id
     */
    @TableField("updator_id")
    private String updatorId;



}

