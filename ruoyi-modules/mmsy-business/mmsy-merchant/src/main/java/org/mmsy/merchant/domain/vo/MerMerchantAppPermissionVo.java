package org.mmsy.merchant.domain.vo;

import java.util.Date;
import org.mmsy.merchant.domain.MerMerchantAppPermission;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）视图对象 mer_merchant_app_permission
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MerMerchantAppPermission.class)
public class MerMerchantAppPermissionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long memberId;

    /**
     * 应用编码（对应app.app_code）
     */
    @ExcelProperty(value = "应用编码")
    private String appCode;

    /**
     * 权限项类型：app-应用, ai_agent-AI智能体
     */
    @ExcelProperty(value = "权限项类型")
    private String itemType;

    /**
     * 购买套餐ID
     */
    @ExcelProperty(value = "购买套餐ID")
    private Long packageId;

    /**
     * 应用过期时间
     */
    @ExcelProperty(value = "应用过期时间")
    private Date expireAt;

    /**
     * 已用子账号数
     */
    @ExcelProperty(value = "已用子账号数")
    private Long userCntUsed;

    /**
     * 子账号上限（0 无限制）
     */
    @ExcelProperty(value = "子账号上限", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=无限制")
    private Long userCntLimit;

    /**
     * 状态：1 正常，0 禁用（后台可手动关闭）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=正常,0=禁用")
    private Long status;

    /**
     * 是否自动续费：1 是，0 否
     */
    @ExcelProperty(value = "是否自动续费", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=是,0=否")
    private Long autoRenew;

}
