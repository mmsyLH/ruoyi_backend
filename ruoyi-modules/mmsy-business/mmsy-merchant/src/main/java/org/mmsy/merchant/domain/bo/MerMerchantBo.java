package org.mmsy.merchant.domain.bo;

import org.mmsy.merchant.domain.MerMerchant;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * 会员/租户业务对象 mer_merchant
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MerMerchant.class, reverseConvertGenerate = false)
public class MerMerchantBo extends BaseEntity {

    /**
     * 会员主键
     */
    @NotNull(message = "会员主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 会员编号（对外展示）
     */
    @NotBlank(message = "会员编号（对外展示）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberCode;

    /**
     * 租户编号（关联sys_tenant.tenant_id"
     */
    private String tenantId;

    /**
     * 公司名称/主体
     */
    @NotBlank(message = "公司名称/主体不能为空", groups = { AddGroup.class, EditGroup.class })
    private String companyName;

    /**
     * 联系"
     */
    @NotBlank(message = "联系人不能为", groups = { AddGroup.class, EditGroup.class })
    private String contactName;

    /**
     * 手机号（登录账号"
     */
    @NotBlank(message = "手机号（登录账号）不能为", groups = { AddGroup.class, EditGroup.class })
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态：1 正常" 禁用
     */
    @NotNull(message = "状态：1 正常禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 注册时间
     */
    @NotNull(message = "注册时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date registerAt;

    /**
     * 账户过期时间（NULL 表示永久"
     */
    private Date expireAt;

    /**
     * 套餐ID
     */
    @NotNull(message = "套餐ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appPackageId;

}

