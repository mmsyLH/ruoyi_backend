package org.mmsy.product.domain.vo;

import org.mmsy.product.domain.ProAppPackage;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 应用套餐视图对象 pro_app_package（包含应用套餐和AI智能体套餐）
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProAppPackage.class)
public class ProAppPackageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 套餐主键
     */
    @ExcelProperty(value = "套餐主键")
    private Long id;

    /**
     * 套餐编码（如 starter/pro/enterprise）
     */
    @ExcelProperty(value = "套餐编码")
    private String packageCode;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String packageName;

    /**
     * 套餐类型：app-应用套餐, ai_agent-AI智能体套餐, hybrid-混合套餐(应用+智能体)
     */
    @ExcelProperty(value = "套餐类型")
    private String packageType;

    /**
     * 应用ID列表（数据库存储字段，逗号分隔）
     */
    @ExcelProperty(value = "应用ID")
    private String appIds;

    /**
     * 应用ID列表（返回给前端）
     */
    private List<Long> appIdList;

    /**
     * AI智能体ID列表（数据库存储字段，逗号分隔）
     */
    @ExcelProperty(value = "智能体ID")
    private String agentIds;

    /**
     * AI智能体ID列表（返回给前端）
     */
    private List<Long> agentIdList;

    /**
     * 月付价格（元）
     */
    @ExcelProperty(value = "月付价格")
    private Long monthPrice;

    /**
     * 年付价格（元）
     */
    @ExcelProperty(value = "年付价格")
    private Long yearPrice;

    /**
     * 试用天数
     */
    @ExcelProperty(value = "试用天数")
    private Long trialDays;

    /**
     * 最大子账号数（0 表示无限制）
     */
    @ExcelProperty(value = "最大子账号数")
    private Long maxUser;

    /**
     * 每月最大请求次数（0 表示无限制，仅AI智能体套餐使用）
     */
    @ExcelProperty(value = "每月最大请求次数")
    private Long maxRequests;

    /**
     * 每月最大Token数（0 表示无限制，仅AI智能体套餐使用）
     */
    @ExcelProperty(value = "每月最大Token数")
    private Long maxTokens;

    /**
     * 支持的AI模型列表(逗号分隔，仅AI智能体套餐使用)
     */
    @ExcelProperty(value = "支持的AI模型")
    private String supportModels;

    /**
     * 套餐描述
     */
    @ExcelProperty(value = "套餐描述")
    private String remark;

    /**
     * 状态：1 上架，0 下架
     */
    @ExcelProperty(value = "状态")
    private Long status;

    /**
     * 前端排序
     */
    @ExcelProperty(value = "排序")
    private Long sortOrder;

}
