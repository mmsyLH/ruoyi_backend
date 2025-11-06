package org.mmsy.merchant.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

import java.io.Serial;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）对象 mer_merchant_app_permission
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mer_merchant_app_permission")
public class MerMerchantAppPermission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 应用编码（对"app.app_code"
     */
    private String appCode;

    /**
     * 权限项类型：app-应用, ai_agent-AI智能"
     */
    private String itemType;

    /**
     * 购买套餐ID
     */
    private Long packageId;

    /**
     * 应用过期时间
     */
    private Date expireAt;

    /**
     * 已用子账号数
     */
    private Long userCntUsed;

    /**
     * 子账号上限（0 无限制）
     */
    private Long userCntLimit;

    /**
     * 1 正常" 禁用（后台可手动关闭"
     */
    private Long status;

    /**
     * 是否自动续费" 是，0 "
     */
    private Long autoRenew;

}

