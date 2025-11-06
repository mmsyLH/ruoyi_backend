package org.mmsy.product.domain.bo;

import org.mmsy.product.domain.ProAppPackage;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 * 应用套餐业务对象 pro_app_package（包含应用套餐和AI智能体套餐）
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProAppPackage.class, reverseConvertGenerate = false)
public class ProAppPackageBo extends BaseEntity {

    /**
     * 套餐主键
     */
    @NotNull(message = "套餐主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 套餐编码（如 starter/pro/enterprise）
     */
    @NotBlank(message = "套餐编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

    /**
     * 套餐类型：app-应用套餐, ai_agent-AI智能体套餐, hybrid-混合套餐(应用+智能体)
     */
    @NotBlank(message = "套餐类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageType;

    /**
     * 应用ID列表（数据库存储字段，逗号分隔）
     */
    private String appIds;

    /**
     * 应用ID列表（前端传入）
     */
    @TableField(exist = false)
    private List<Long> appIdList;

    /**
     * AI智能体ID列表（数据库存储字段，逗号分隔）
     */
    private String agentIds;

    /**
     * AI智能体ID列表（前端传入）
     */
    @TableField(exist = false)
    private List<Long> agentIdList;

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
     * 状态：1 上架，0 下架
     */
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 前端排序
     */
    private Long sortOrder;

    /**
     * 查询开关：列表是否返回 appIdList（默认不返回）
     */
    @TableField(exist = false)
    private Boolean includeAppIds;

}
