package org.mmsy.merchant.domain.vo;

import java.util.Date;
import org.mmsy.merchant.domain.MerMerchant;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 会员/租户视图对象 mer_merchant
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MerMerchant.class)
public class MerMerchantVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会员主键
     */
    @ExcelProperty(value = "会员主键")
    private Long id;

    /**
     * 会员编号（对外展示）
     */
    @ExcelProperty(value = "会员编号")
    private String memberCode;

    /**
     * 租户编号（关联sys_tenant.tenant_id）
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;

    /**
     * 公司名称/主体
     */
    @ExcelProperty(value = "公司名称/主体")
    private String companyName;

    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人")
    private String contactName;

    /**
     * 手机号（登录账号）
     */
    @ExcelProperty(value = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 状态：1 正常，0 禁用
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=正常,0=禁用")
    private Long status;

    /**
     * 注册时间
     */
    @ExcelProperty(value = "注册时间")
    private Date registerAt;

    /**
     * 账户过期时间（NULL 表示永久）
     */
    @ExcelProperty(value = "账户过期时间")
    private Date expireAt;

    /**
     * 套餐ID
     */
    @ExcelProperty(value = "套餐ID")
    private Long appPackageId;

}
