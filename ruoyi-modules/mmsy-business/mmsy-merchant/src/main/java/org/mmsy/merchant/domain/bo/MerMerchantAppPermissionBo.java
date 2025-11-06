package org.mmsy.merchant.domain.bo;

import org.mmsy.merchant.domain.MerMerchantAppPermission;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）业务对象 mer_merchant_app_permission
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MerMerchantAppPermission.class, reverseConvertGenerate = false)
public class MerMerchantAppPermissionBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "购买套餐ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageId;

    /**
     * 应用过期时间
     */
    @NotNull(message = "应用过期时间不能为空", groups = { AddGroup.class, EditGroup.class })
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

