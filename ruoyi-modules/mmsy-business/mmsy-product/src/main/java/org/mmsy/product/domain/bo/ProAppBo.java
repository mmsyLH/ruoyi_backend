package org.mmsy.product.domain.bo;

import org.mmsy.product.domain.ProApp;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 中控平台应用业务对象 pro_app
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProApp.class, reverseConvertGenerate = false)
public class        ProAppBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 应用唯一编码，如 scm、listing
     */
    @NotBlank(message = "应用唯一编码，如 scm、listing不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appCode;

    /**
     * 应用名称，如供应链系统
     */
    @NotBlank(message = "应用名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appName;

    /**
     * 应用简介
     */
    private String appDesc;

    /**
     * 应用详细介绍(富文本)
     */
    private String appDetail;

    /**
     * 功能特性列表["特性1","特性2"]
     */
    private String features;

    /**
     * 应用图标文件ID（关联sys_oss表oss_id）
     */
    private Long appIcon;

    /**
     * 应用类型(internal-内部系统,external-第三方系统)
     */
    @NotBlank(message = "应用类型(internal-内部系统,external-第三方系统)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appType;

    /**
     * 应用分类对应字典表app_category
     */
    @NotBlank(message = "应用分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appCategory;

    /**
     * 应用登录地址(支持SSO)
     */
    private String loginUrl;

    /**
     * 演示地址
     */
    private String demoUrl;

    /**
     * 应用参数配置
     */
    @NotBlank(message = "应用参数配置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String config;

    /**
     * 状态：1 启用，0 禁用
     */
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 前端展示排序，升序
     */
    private Long sortOrder;

    /**
     * 应用版本号
     */
    private String version;

    /**
     * 更新日志
     */
    private String updateLog;

    /**
     * 备注
     */
    private String remark;

}
