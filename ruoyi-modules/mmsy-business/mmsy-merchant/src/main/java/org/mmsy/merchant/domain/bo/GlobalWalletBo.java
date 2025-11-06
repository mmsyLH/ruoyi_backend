package org.mmsy.merchant.domain.bo;

import org.mmsy.merchant.domain.GlobalWallet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 全球钱包业务对象 global_wallet
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = GlobalWallet.class, reverseConvertGenerate = false)
public class GlobalWalletBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 币种
     */
    @NotBlank(message = "币种不能为空", groups = { AddGroup.class, EditGroup.class })
    private String currency;

    /**
     * 总入"
     */
    private Long amountIn;

    /**
     * 总出"
     */
    private Long amountOut;

    /**
     * 可用余额
     */
    private Long amountLeft;

    /**
     * 待入账金"
     */
    private Long amountPending;

    /**
     * 冻结资金
     */
    private Long amountFreeze;

    /**
     * 总金"
     */
    private Long amountTotal;

    /**
     * 是否锁定(0正常 1锁定)
     */
    private Long isLock;

    /**
     * 钱包编号
     */
    private String walletNo;

    /**
     * 上游钱包ID
     */
    private String upstreamId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 交易描述
     */
    private String description;

}
