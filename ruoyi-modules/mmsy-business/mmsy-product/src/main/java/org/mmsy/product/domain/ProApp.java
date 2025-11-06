package org.mmsy.product.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 中控平台应用对象 pro_app
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pro_app")
public class ProApp extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 应用唯一编码，如 scm、listing
     */
    private String appCode;

    /**
     * 应用名称，如供应链系统
     */
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
    private String appType;

    /**
     * 应用分类对应字典表app_category
     */
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
    private String config;

    /**
     * 状态：1 启用，0 禁用
     */
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
