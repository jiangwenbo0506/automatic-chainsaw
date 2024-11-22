package com.blk.model.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 珠海航展-负载率曲线列表实体类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:29
 */
@Data
@Accessors(chain = true)
@TableName("zhdh_transform_playload")
public class TransformPlayload {

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
     * 负载率
     */
    @TableField("transform_playload")
    private Object transformPlayload;

    /**
     * 配变压器名称
     */
    @TableField("transform_name")
    private String transformName;

    /**
     * 所属网关标识
     */
    @TableField("gateway_identifier")
    private String gatewayIdentifier;

    /**
     * 电房名称
     */
    @TableField("station_name")
    private String stationName;

    /**
     * 配变变压器容量
     */
    @TableField("transform_capacity")
    private String transformCapacity;

    
}

