package org.mmsy.merchant.service;

import org.mmsy.merchant.domain.vo.GlobalWalletVo;
import org.mmsy.merchant.domain.bo.GlobalWalletBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 全球钱包Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IGlobalWalletService {

    /**
     * 查询全球钱包
     *
     * @param id 主键
     * @return 全球钱包
     */
    GlobalWalletVo queryById(Long id);

    /**
     * 分页查询全球钱包列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 全球钱包分页列表
     */
    TableDataInfo<GlobalWalletVo> queryPageList(GlobalWalletBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的全球钱包列"
     *
     * @param bo 查询条件
     * @return 全球钱包列表
     */
    List<GlobalWalletVo> queryList(GlobalWalletBo bo);

    /**
     * 新增全球钱包
     *
     * @param bo 全球钱包
     * @return 是否新增成功
     */
    Boolean insertByBo(GlobalWalletBo bo);

    /**
     * 修改全球钱包
     *
     * @param bo 全球钱包
     * @return 是否修改成功
     */
    Boolean updateByBo(GlobalWalletBo bo);

    /**
     * 校验并批量删除全球钱包信"
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校"
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 根据租户ID和币种查询钱"
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @return 钱包信息
     */
    GlobalWalletVo queryByTenantIdAndCurrency(String tenantId, String currency);

    /**
     * 充值（入账"
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @param amount 充值金额（单位：分"
     * @param businessType 业务类型"充"7调账等）
     * @param businessId 业务ID
     * @param businessNo 业务单号
     * @param description 描述
     * @return 充值结"
     */
    Boolean recharge(String tenantId, String currency, Long amount, String businessType,
                    Long businessId, String businessNo, String description);

    /**
     * 消费（出账）
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @param amount 消费金额（单位：分）
     * @param businessType 业务类型"消费 3购买套餐等）
     * @param businessId 业务ID
     * @param businessNo 业务单号
     * @param description 描述
     * @return 消费结果
     */
    Boolean consume(String tenantId, String currency, Long amount, String businessType,
                   Long businessId, String businessNo, String description);

    /**
     * 退款（入账"
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @param amount 退款金额（单位：分"
     * @param businessId 业务ID
     * @param businessNo 业务单号
     * @param description 描述
     * @return 退款结"
     */
    Boolean refund(String tenantId, String currency, Long amount,
                  Long businessId, String businessNo, String description);

    /**
     * 查询钱包余额
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @return 可用余额（单位：分），如果钱包不存在返回0
     */
    Long queryBalance(String tenantId, String currency);

    /**
     * 创建钱包（如果不存在"
     *
     * @param tenantId 租户ID
     * @param merchantId 商户ID
     * @param currency 币种
     * @return 创建结果，true表示钱包已存在或创建成功，false表示创建失败
     */
    Boolean createWalletIfNotExists(String tenantId, Long merchantId, String currency);
}
