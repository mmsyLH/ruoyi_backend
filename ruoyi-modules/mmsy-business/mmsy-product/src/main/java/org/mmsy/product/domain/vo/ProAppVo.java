package org.mmsy.product.domain.vo;

import org.mmsy.product.domain.ProApp;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 中控平台应用视图对象 pro_app
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProApp.class)
public class ProAppVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 应用唯一编码，如 scm、listing
     */
    @ExcelProperty(value = "应用唯一编码，如 scm、listing")
    private String appCode;

    /**
     * 应用名称，如供应链系统
     */
    @ExcelProperty(value = "应用名称，如供应链系统")
    private String appName;

    /**
     * 应用简介
     */
    @ExcelProperty(value = "应用简介")
    private String appDesc;

    /**
     * 应用详细介绍(富文本)
     */
    @ExcelProperty(value = "应用详细介绍(富文本)")
    private String appDetail;

    /**
     * 功能特性列表["特性1","特性2"]
     */
    @ExcelProperty(value = "功能特性列表")
    private String features;

    /**
     * 应用图标文件ID（关联sys_oss表oss_id，存储在超级租户000000中）
     */
    @ExcelProperty(value = "应用图标文件ID")
    private Long appIcon;

    /**
     * 应用图标URL（自动翻译，忽略租户隔离）
     */
//    @Translation(type = TransConstant.OSS_ID_TO_URL_IGNORE_TENANT, mapper = "appIcon")
    private String appIconUrl;

    /**
     * 应用类型(internal-内部系统,external-第三方系统)
     */
    private String appType;

    /**
     * 应用分类对应字典表app_category
     */
    @ExcelProperty(value = "应用分类")
    private String appCategory;

    /**
     * 应用登录地址(支持SSO)
     */
    @ExcelProperty(value = "应用登录地址(支持SSO)")
    private String loginUrl;

    /**
     * 演示地址
     */
    @ExcelProperty(value = "演示地址")
    private String demoUrl;

    /**
     * 应用参数配置
     */
    @ExcelProperty(value = "应用参数配置")
    private String config;

    /**
     * 状态：1 启用，0 禁用
     */
    @ExcelProperty(value = "状态")
    private Long status;

    /**
     * 前端展示排序，升序
     */
    @ExcelProperty(value = "排序")
    private Long sortOrder;

    /**
     * 应用版本号
     */
    @ExcelProperty(value = "版本")
    private String version;

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
