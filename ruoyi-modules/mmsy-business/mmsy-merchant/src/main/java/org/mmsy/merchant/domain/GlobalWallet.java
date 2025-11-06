package org.mmsy.merchant.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 全球钱包对象 global_wallet
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("global_wallet")
public class GlobalWallet extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
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


}
