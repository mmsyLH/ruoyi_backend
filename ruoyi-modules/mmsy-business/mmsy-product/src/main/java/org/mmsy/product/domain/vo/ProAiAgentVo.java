package org.mmsy.product.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.mmsy.product.domain.ProAiAgent;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * AI智能体视图对象 pro_ai_agent
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProAiAgent.class)
public class ProAiAgentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * AI智能体唯一编码
     */
    @ExcelProperty(value = "AI智能体唯一编码")
    private String agentCode;

    /**
     * AI智能体名称
     */
    private String agentName;

    /**
     * AI智能体简介
     */
    private String agentDesc;

    /**
     * AI智能体详细介绍(富文本)
     */
    @ExcelProperty(value = "AI智能体详细介绍(富文本)")
    private String agentDetail;

    /**
     * 功能特性列表["特性1","特性2"]
     */
    private String features;

    /**
     * AI智能体图标文件ID（关联sys_oss表oss_id）
     */
    @ExcelProperty(value = "AI智能体图标文件ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关联sys_oss表oss_id")
    private Long agentIcon;

    /**
     * AI智能体图标URL
     */
//    @Translation(type = TransConstant.OSS_ID_TO_URL_IGNORE_TENANT, mapper = "agentIcon")
    private String agentIconUrl;

    /**
     * AI智能体类型(general-通用,professional-专业,custom-定制)
     */
    @ExcelProperty(value = "AI智能体类型(general-通用,professional-专业,custom-定制)")
    private String agentType;

    /**
     * AI智能体分类对应字典表ai_agent_category
     */
    @ExcelProperty(value = "AI智能体分类对应字典表ai_agent_category")
    private String agentCategory;

    /**
     * AI智能体访问地址
     */
    @ExcelProperty(value = "AI智能体访问地址")
    private String accessUrl;

    /**
     * 演示地址
     */
    @ExcelProperty(value = "演示地址")
    private String demoUrl;

    /**
     * AI模型配置(JSON格式,包含模型名称、参数等)
     */
    @ExcelProperty(value = "AI模型配置(JSON格式,包含模型名称、参数等)")
    private String modelConfig;

    /**
     * 提示词模板
     */
    private String promptTemplate;

    /**
     * 关联的知识库ID列表(逗号分隔)
     */
    @ExcelProperty(value = "关联的知识库ID列表(逗号分隔)")
    private String knowledgeBaseIds;

    /**
     * 智能体参数配置
     */
    private String config;

    /**
     * 状态：1 启用，0 禁用
     */
    @ExcelProperty(value = "状态：1 启用，0 禁用")
    private Long status;

    /**
     * 前端展示排序，升序
     */
    private Long sortOrder;

    /**
     * 更新日志
     */
    @ExcelProperty(value = "更新日志")
    private String updateLog;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
