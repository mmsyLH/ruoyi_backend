package org.mmsy.product.domain.bo;

import org.mmsy.product.domain.ProAiAgent;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * AI智能体业务对象 pro_ai_agent
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProAiAgent.class, reverseConvertGenerate = false)
public class ProAiAgentBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * AI智能体唯一编码
     */
    @NotBlank(message = "AI智能体唯一编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String agentCode;

    /**
     * AI智能体名称
     */
    @NotBlank(message = "AI智能体名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String agentName;

    /**
     * AI智能体简介
     */
    private String agentDesc;

    /**
     * AI智能体详细介绍(富文本)
     */
    private String agentDetail;

    /**
     * 功能特性列表["特性1","特性2"]
     */
    private String features;

    /**
     * AI智能体图标文件ID（关联sys_oss表oss_id）
     */
    private Long agentIcon;

    /**
     * AI智能体类型(general-通用,professional-专业,custom-定制)
     */
    @NotBlank(message = "AI智能体类型(general-通用,professional-专业,custom-定制)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String agentType;

    /**
     * AI智能体分类对应字典表ai_agent_category
     */
    private String agentCategory;

    /**
     * AI智能体访问地址
     */
    @NotBlank(message = "AI智能体访问地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String accessUrl;

    /**
     * 演示地址
     */
    private String demoUrl;

    /**
     * AI模型配置(JSON格式,包含模型名称、参数等)
     */
    private String modelConfig;

    /**
     * 提示词模板
     */
    private String promptTemplate;

    /**
     * 关联的知识库ID列表(逗号分隔)
     */
    private String knowledgeBaseIds;

    /**
     * 智能体参数配置
     */
    private String config;

    /**
     * 状态：1 启用，0 禁用
     */
    @NotNull(message = "状态：1 启用，0 禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 前端展示排序，升序
     */
    private Long sortOrder;

    /**
     * 更新日志
     */
    private String updateLog;

    /**
     * 备注
     */
    private String remark;

}
