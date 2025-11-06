package org.mmsy.merchant.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

import java.io.Serial;

/**
 * 会员/租户对象 mer_merchant
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mer_merchant")
public class MerMerchant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会员主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 会员编号（对外展示）
     */
    private String memberCode;

    /**
     * 租户编号（关联sys_tenant.tenant_id"
     */
    private String tenantId;

    /**
     * 公司名称/主体
     */
    private String companyName;

    /**
     * 联系"
     */
    private String contactName;

    /**
     * 手机号（登录账号"
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态：1 正常" 禁用
     */
    private Long status;

    /**
     * 注册时间
     */
    private Date registerAt;

    /**
     * 账户过期时间（NULL 表示永久"
     */
    private Date expireAt;

    /**
     * 套餐ID
     */
    private Long appPackageId;

}

