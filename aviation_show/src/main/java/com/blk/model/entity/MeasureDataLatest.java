package com.blk.model.entity;


import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 珠海航展-低压开关实时数据表实体类
 *
 * @author Wilbur.J
 * @since 2024-10-28 18:16:27
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("zhdh_measure_data_latest")
public class MeasureDataLatest {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人id
     */
    @TableField("updator_id")
    private String updatorId;

    /**
     * 组织id
     */
    @TableField("org_id")
    private String orgId;

    /**
     * 产品id
     */
    @TableField("product_id")
    private String productId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private String appId;

    /**
     * 数据时间
     */
    @TableField("event_time")
    private Date eventTime;

    /**
     * 网关设备身份标识
     */
    @TableField("gateway_identifier")
    private String gatewayIdentifier;

    /**
     * 服务id
     */
    @TableField("service_id")
    private String serviceId;

    /**
     * 设备id
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 网关id
     */
    @TableField("gateway_id")
    private String gatewayId;

    /**
     * 设备id
     */
    @TableField("device_identifier")
    private String deviceIdentifier;

    /**
     * A相电流
     */
    @TableField("tgia")
    private BigDecimal tgia;

    /**
     * B相电流
     */
    @TableField("tgib")
    private BigDecimal tgib;

    /**
     * C相电流
     */
    @TableField("tgic")
    private BigDecimal tgic;

    /**
     * 开关状态（分闸/合闸）
     */
    @TableField("swicth_status")
    private String swicthStatus;

    /**
     * 开关编号
     */
    @TableField("swicth_no")
    private String swicthNo;

    /**
     * 所在站房名称
     */
    @TableField("station_name")
    private String stationName;

    /**
     * 配变名称
     */
    @TableField("transform_name")
    private String transformName;

    /**
     * 开关额定电流
     */
    @TableField("rated_current")
    private String ratedCurrent;

    /**
     * 配变变压器容量
     */
    @TableField("transform_capacity")
    private String transformCapacity;

    
}

