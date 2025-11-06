package org.mmsy.merchant.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.mmsy.merchant.domain.GlobalWalletFlow;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 全球钱包流水视图对象 global_wallet_flow
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GlobalWalletFlow.class)
public class GlobalWalletFlowVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 商户ID
     */
    @ExcelProperty(value = "商户ID")
    private Long merchantId;

    /**
     * 钱包ID
     */
    @ExcelProperty(value = "钱包ID")
    private Long walletId;

    /**
     * 币种(USD/CNY/EUR"
     */
    @ExcelProperty(value = "币种(USD/CNY/EUR")
    private String currency;

    /**
     * 交易金额
     */
    @ExcelProperty(value = "交易金额")
    private Long amount;

    /**
     * 收支类型(0出账 1入账)
     */
    @ExcelProperty(value = "收支类型(0出账 1入账)")
    private String amountType;

    /**
     * 流水类型(1充"2提现 3转账收款 4转账付款 5收款 6付款 7退"8手续"9冻结 10解冻 11调账)
     */

    private String flowType;

    /**
     * 变动前金"
     */
    @ExcelProperty(value = "变动前金")
    private Long amountBefore;

    /**
     * 变动后金"
     */
    @ExcelProperty(value = "变动后金")
    private Long amountAfter;

    /**
     * 手续"
     */
    @ExcelProperty(value = "手续")
    private Long fee;

    /**
     * 总手续费
     */
    @ExcelProperty(value = "总手续费")
    private Long totalFee;

    /**
     * 交易时间
     */
    @ExcelProperty(value = "交易时间")
    private Date tranTime;

    /**
     * 业务类型(1充"2内部转账收款 3内部转账付款 4提现 5付款 6退"7调账)
     */

    private String businessType;

    /**
     * 业务ID
     */
    @ExcelProperty(value = "业务ID")
    private Long businessId;

    /**
     * 业务单号
     */
    @ExcelProperty(value = "业务单号")
    private String businessNo;

    /**
     * 支付通道编码
     */
    @ExcelProperty(value = "支付通道编码")
    private String channelCode;

    /**
     * 上游交易ID
     */
    @ExcelProperty(value = "上游交易ID")
    private String transactionId;

    /**
     * 交易状"0待处"1成功 2失败 3已关"
     */

    private String transactionStatus;

    /**
     * 交易对手"
     */
    @ExcelProperty(value = "交易对手")
    private String counterParty;

    /**
     * 对手方邮"
     */
    @ExcelProperty(value = "对手方邮")
    private String toEmail;

    /**
     * 对方钱包ID(内部转账)
     */
    @ExcelProperty(value = "对方钱包ID(内部转账)")
    private Long oppositeWalletId;

    /**
     * 对方商户ID
     */
    @ExcelProperty(value = "对方商户ID")
    private Long oppositeMerchantId;

    /**
     * 对方账户(外部账户)
     */
    @ExcelProperty(value = "对方账户(外部账户)")
    private String oppositeAccount;

    /**
     * 对方户名
     */
    @ExcelProperty(value = "对方户名")
    private String oppositeName;

    /**
     * 来源地址
     */
    @ExcelProperty(value = "来源地址")
    private String fromAddress;

    /**
     * 目标地址
     */
    @ExcelProperty(value = "目标地址")
    private String toAddress;

    /**
     * 交易哈希
     */
    @ExcelProperty(value = "交易哈希")
    private String txHash;

    /**
     * 网络类型
     */
    @ExcelProperty(value = "网络类型")
    private String networkType;

    /**
     * 交易消息
     */
    @ExcelProperty(value = "交易消息")
    private String tranMsg;

    /**
     * 交易描述
     */
    @ExcelProperty(value = "交易描述")
    private String description;

    /**
     * 交易备注
     */
    @ExcelProperty(value = "交易备注")
    private String remark;


}
