package com.blk.model.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 珠海航展-告警列表实体类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Data
@Accessors(chain = true)
@TableName("zhdh_warn")
public class Warn{

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

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
     * 事件时间
     */
    @TableField("event_time")
    private Date eventTime;

    /**
     * 告警内容
     */
    @TableField("content")
    private String content;

    /**
     * 告警设备
     */
    @TableField("device")
    private String device;

    /**
     * 所在电房名称
     */
    @TableField("station_name")
    private String stationName;

    /**
     * 网关唯一标识
     */
    @TableField("gateway_identifier")
    private String gatewayIdentifier;

    /**
     * 开关编号
     */
    @TableField("switch_no")
    private String switchNo;

    /**
     * 柜编号
     */
    @TableField("cabinet_no")
    private String cabinetNo;

    /**
     * 所属变压名称
     */
    @TableField("transform_name")
    private String transformName;

    
}

