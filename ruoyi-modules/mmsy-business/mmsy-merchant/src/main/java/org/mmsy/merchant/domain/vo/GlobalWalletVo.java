package org.mmsy.merchant.domain.vo;

import org.mmsy.merchant.domain.GlobalWallet;
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
 * 全球钱包视图对象 global_wallet
 *
 * @author luohan
 * @date 2025-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GlobalWallet.class)
public class GlobalWalletVo implements Serializable {

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
     * 币种
     */
    @ExcelProperty(value = "币种")
    private String currency;

    /**
     * 总入"
     */
    @ExcelProperty(value = "总入")
    private Long amountIn;

    /**
     * 总出"
     */
    @ExcelProperty(value = "总出")
    private Long amountOut;

    /**
     * 可用余额
     */
    @ExcelProperty(value = "可用余额")
    private Long amountLeft;

    /**
     * 待入账金"
     */
    @ExcelProperty(value = "待入账金")
    private Long amountPending;

    /**
     * 冻结资金
     */
    @ExcelProperty(value = "冻结资金")
    private Long amountFreeze;

    /**
     * 总金"
     */
    @ExcelProperty(value = "总金")
    private Long amountTotal;

    /**
     * 是否锁定(0正常 1锁定)
     */
    @ExcelProperty(value = "是否锁定(0正常 1锁定)")
    private Long isLock;

    /**
     * 钱包编号
     */
    @ExcelProperty(value = "钱包编号")
    private String walletNo;

    /**
     * 上游钱包ID
     */
    @ExcelProperty(value = "上游钱包ID")
    private String upstreamId;

    /**
     * 通道编码
     */
    @ExcelProperty(value = "通道编码")
    private String channelCode;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
