package org.mmsy.product.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用套餐对象 pro_app_package（包含应用套餐和AI智能体套餐）
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pro_app_package")
public class ProAppPackage extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 套餐主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 套餐编码（如 starter/pro/enterprise）
     */
    private String packageCode;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐类型：app-应用套餐, ai_agent-AI智能体套餐, hybrid-混合套餐(应用+智能体)

     */
    private String packageType;

    /**
     * 应用ID列表(逗号分隔，关联pro_app.id)
     */
    private String appIds;

    /**
     * AI智能体ID列表(逗号分隔，关联pro_ai_agent.id)
     */
    private String agentIds;

    /**
     * 月付价格（元）
     */
    private Long monthPrice;

    /**
     * 年付价格（元）
     */
    private Long yearPrice;

    /**
     * 试用天数
     */
    private Long trialDays;

    /**
     * 最大子账号数（0 表示无限制）
     */
    private Long maxUser;

    /**
     * 每月最大请求次数（0 表示无限制，仅AI智能体套餐使用）
     */
    private Long maxRequests;

    /**
     * 每月最大Token数（0 表示无限制，仅AI智能体套餐使用）
     */
    private Long maxTokens;

    /**
     * 支持的AI模型列表(逗号分隔，仅AI智能体套餐使用)
     */
    private String supportModels;

    /**
     * 套餐描述
     */
    private String remark;

    /**
     * 1 上架，0 下架
     */
    private Long status;

    /**
     * 前端排序
     */
    private Long sortOrder;

}
