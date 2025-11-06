package org.mmsy.merchant.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.tenant.helper.TenantHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmsy.merchant.domain.GlobalWalletFlow;
import org.mmsy.merchant.mapper.GlobalWalletFlowMapper;
import org.springframework.stereotype.Service;
import org.mmsy.merchant.domain.bo.GlobalWalletBo;
import org.mmsy.merchant.domain.vo.GlobalWalletVo;
import org.mmsy.merchant.domain.GlobalWallet;
import org.mmsy.merchant.mapper.GlobalWalletMapper;
import org.mmsy.merchant.service.IGlobalWalletService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Collection;

/**
 * 全球钱包Service业务层处"
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GlobalWalletServiceImpl implements IGlobalWalletService {

    private final GlobalWalletMapper baseMapper;
    private final GlobalWalletFlowMapper walletFlowMapper;

    /**
     * 查询全球钱包
     *
     * @param id 主键
     * @return 全球钱包
     */
    @Override
    public GlobalWalletVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询全球钱包列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 全球钱包分页列表
     */
    @Override
    public TableDataInfo<GlobalWalletVo> queryPageList(GlobalWalletBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<GlobalWallet> lqw = buildQueryWrapper(bo);
        Page<GlobalWalletVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的全球钱包列"
     *
     * @param bo 查询条件
     * @return 全球钱包列表
     */
    @Override
    public List<GlobalWalletVo> queryList(GlobalWalletBo bo) {
        LambdaQueryWrapper<GlobalWallet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<GlobalWallet> buildQueryWrapper(GlobalWalletBo bo) {
        LambdaQueryWrapper<GlobalWallet> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(GlobalWallet::getId);
        lqw.eq(bo.getMerchantId() != null, GlobalWallet::getMerchantId, bo.getMerchantId());
        lqw.eq(StringUtils.isNotBlank(bo.getCurrency()), GlobalWallet::getCurrency, bo.getCurrency());
        lqw.eq(bo.getAmountIn() != null, GlobalWallet::getAmountIn, bo.getAmountIn());
        lqw.eq(bo.getAmountOut() != null, GlobalWallet::getAmountOut, bo.getAmountOut());
        lqw.eq(bo.getAmountLeft() != null, GlobalWallet::getAmountLeft, bo.getAmountLeft());
        lqw.eq(bo.getAmountPending() != null, GlobalWallet::getAmountPending, bo.getAmountPending());
        lqw.eq(bo.getAmountFreeze() != null, GlobalWallet::getAmountFreeze, bo.getAmountFreeze());
        lqw.eq(bo.getAmountTotal() != null, GlobalWallet::getAmountTotal, bo.getAmountTotal());
        lqw.eq(bo.getIsLock() != null, GlobalWallet::getIsLock, bo.getIsLock());
        lqw.eq(StringUtils.isNotBlank(bo.getWalletNo()), GlobalWallet::getWalletNo, bo.getWalletNo());
        lqw.eq(StringUtils.isNotBlank(bo.getUpstreamId()), GlobalWallet::getUpstreamId, bo.getUpstreamId());
        lqw.eq(StringUtils.isNotBlank(bo.getChannelCode()), GlobalWallet::getChannelCode, bo.getChannelCode());
        return lqw;
    }

    /**
     * 新增全球钱包
     *
     * @param bo 全球钱包
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(GlobalWalletBo bo) {
        // 外层已通过 TenantHelper.dynamic() 设置租户上下文，此处不需要额外处"
        GlobalWallet add = MapstructUtils.convert(bo, GlobalWallet.class);
        // 生成钱包编号
        add.setWalletNo(generateWalletNo());
        // 初始化余额为0
        add.setAmountIn(0L);
        add.setAmountOut(0L);
        add.setAmountLeft(0L);
        add.setAmountPending(0L);
        add.setAmountFreeze(0L);
        add.setAmountTotal(0L);
        add.setIsLock(0L);

        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
            log.info("创建钱包成功, 租户ID: {}, 币种: {}, 钱包编号: {}", bo.getTenantId(), bo.getCurrency(), add.getWalletNo());
        }
        return flag;
    }

    /**
     * 修改全球钱包
     *
     * @param bo 全球钱包
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(GlobalWalletBo bo) {
        GlobalWallet update = MapstructUtils.convert(bo, GlobalWallet.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(GlobalWallet entity){
        //TODO 做一些数据校"如唯一约束
    }

    /**
     * 校验并批量删除全球钱包信"
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校"
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校"判断是否需要校"
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 根据租户ID和币种查询钱"
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @return 钱包信息
     */
    @Override
    public GlobalWalletVo queryByTenantIdAndCurrency(String tenantId, String currency) {
        LambdaQueryWrapper<GlobalWallet> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GlobalWallet::getTenantId, tenantId);
        wrapper.eq(GlobalWallet::getCurrency, currency);
        return baseMapper.selectVoOne(wrapper);
    }

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean recharge(String tenantId, String currency, Long amount, String businessType,
                           Long businessId, String businessNo, String description) {
        // 使用 TenantHelper.dynamic() 设置租户上下文（跨服务调用时租户上下文不会自动传递）
        return TenantHelper.dynamic(tenantId, () -> {
            // 参数校验
            if (amount == null || amount <= 0) {
                throw new ServiceException("充值金额必须大");
            }

            // 查询钱包
            GlobalWalletVo walletVo = queryByTenantIdAndCurrency(tenantId, currency);
            if (walletVo == null) {
                throw new ServiceException("钱包不存在，请先创建钱包");
            }

            // 检查钱包是否锁"
            if (walletVo.getIsLock() != null && walletVo.getIsLock() == 1L) {
                throw new ServiceException("钱包已锁定，无法充");
            }

            // 更新钱包余额
            GlobalWallet wallet = new GlobalWallet();
            wallet.setId(walletVo.getId());
            wallet.setAmountIn(walletVo.getAmountIn() + amount);
            wallet.setAmountLeft(walletVo.getAmountLeft() + amount);
            wallet.setAmountTotal(walletVo.getAmountTotal() + amount);

            boolean updateResult = baseMapper.updateById(wallet) > 0;
            if (!updateResult) {
                throw new ServiceException("更新钱包余额失败");
            }

            // 创建流水记录（框架会自动填充 tenant_id 和审计字段）
            GlobalWalletFlow flow = new GlobalWalletFlow();
            flow.setTenantId(tenantId); // 显式设置租户ID，保持代码清"
            flow.setMerchantId(walletVo.getMerchantId());
            flow.setWalletId(walletVo.getId());
            flow.setCurrency(currency);
            flow.setAmount(amount);
            flow.setAmountType("1"); // 1入账
            flow.setFlowType(businessType); // 业务类型
            flow.setAmountBefore(walletVo.getAmountLeft());
            flow.setAmountAfter(walletVo.getAmountLeft() + amount);
            flow.setFee(0L);
            flow.setTotalFee(0L);
            flow.setTranTime(new Date());
            flow.setBusinessType(businessType);
            flow.setBusinessId(businessId);
            flow.setBusinessNo(businessNo);
            flow.setTransactionStatus("1"); // 1成功
            flow.setDescription(description);

            boolean flowResult = walletFlowMapper.insert(flow) > 0;
            if (!flowResult) {
                throw new ServiceException("创建流水记录失败");
            }

            return true;
        });
    }

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean consume(String tenantId, String currency, Long amount, String businessType,
                          Long businessId, String businessNo, String description) {
        // 使用 TenantHelper.dynamic() 设置租户上下文（跨服务调用时租户上下文不会自动传递）
        return TenantHelper.dynamic(tenantId, () -> {
            try {
                // 参数校验
                if (amount == null || amount <= 0) {
                    throw new ServiceException("消费金额必须大于0");
                }

                // 查询钱包
                GlobalWalletVo walletVo = queryByTenantIdAndCurrency(tenantId, currency);
                if (walletVo == null) {
                    throw new ServiceException("钱包不存");
                }

                // 检查钱包是否锁"
                if (walletVo.getIsLock() != null && walletVo.getIsLock() == 1L) {
                    throw new ServiceException("钱包已锁定，无法消费");
                }

                // 检查余额是否足"
                if (walletVo.getAmountLeft() < amount) {
                    throw new ServiceException("余额不足");
                }

                // 更新钱包余额
                GlobalWallet wallet = new GlobalWallet();
                wallet.setId(walletVo.getId());
                wallet.setAmountOut(walletVo.getAmountOut() + amount);
                wallet.setAmountLeft(walletVo.getAmountLeft() - amount);
                wallet.setAmountTotal(walletVo.getAmountTotal() - amount);

                boolean updateResult = baseMapper.updateById(wallet) > 0;
                if (!updateResult) {
                    throw new ServiceException("更新钱包余额失败");
                }

                // 创建流水记录（框架会自动填充 tenant_id 和审计字段）
                GlobalWalletFlow flow = new GlobalWalletFlow();
                flow.setTenantId(tenantId); // 显式设置租户ID，保持代码清"
                flow.setMerchantId(walletVo.getMerchantId());
                flow.setWalletId(walletVo.getId());
                flow.setCurrency(currency);
                flow.setAmount(amount);
                flow.setAmountType("2"); // 2出账
                flow.setFlowType(businessType); // 业务类型
                flow.setAmountBefore(walletVo.getAmountLeft());
                flow.setAmountAfter(walletVo.getAmountLeft() - amount);
                flow.setFee(0L);
                flow.setTotalFee(0L);
                flow.setTranTime(new Date());
                flow.setBusinessType(businessType);
                flow.setBusinessId(businessId);
                flow.setBusinessNo(businessNo);
                flow.setTransactionStatus("1"); // 1成功
                flow.setDescription(description);

                boolean flowResult = walletFlowMapper.insert(flow) > 0;
                if (!flowResult) {
                    throw new ServiceException("创建流水记录失败");
                }

                log.info("消费成功, 租户ID: {}, 币种: {}, 消费金额: {}, 业务类型: {}", tenantId, currency, amount, businessType);
                return true;
            } catch (Exception e) {
                log.error("消费失败", e);
                throw new ServiceException("消费失败: " + e.getMessage());
            }
        });
    }

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean refund(String tenantId, String currency, Long amount,
                         Long businessId, String businessNo, String description) {
        // 使用 TenantHelper.dynamic() 设置租户上下文（跨服务调用时租户上下文不会自动传递）
        return TenantHelper.dynamic(tenantId, () -> {
            try {
                // 参数校验
                if (amount == null || amount <= 0) {
                    throw new ServiceException("退款金额必须大");
                }

                // 查询钱包
                GlobalWalletVo walletVo = queryByTenantIdAndCurrency(tenantId, currency);
                if (walletVo == null) {
                    throw new ServiceException("钱包不存");
                }

                // 检查钱包是否锁"
                if (walletVo.getIsLock() != null && walletVo.getIsLock() == 1L) {
                    throw new ServiceException("钱包已锁定，无法退");
                }

                // 更新钱包余额（退款是出账的逆操作，所以减少amountOut，增加amountLeft"
                GlobalWallet wallet = new GlobalWallet();
                wallet.setId(walletVo.getId());
                wallet.setAmountOut(walletVo.getAmountOut() - amount);
                wallet.setAmountLeft(walletVo.getAmountLeft() + amount);
                wallet.setAmountTotal(walletVo.getAmountTotal() + amount);

                boolean updateResult = baseMapper.updateById(wallet) > 0;
                if (!updateResult) {
                    throw new ServiceException("更新钱包余额失败");
                }

                // 创建流水记录（框架会自动填充 tenant_id 和审计字段）
                GlobalWalletFlow flow = new GlobalWalletFlow();
                flow.setTenantId(tenantId); // 显式设置租户ID，保持代码清"
                flow.setMerchantId(walletVo.getMerchantId());
                flow.setWalletId(walletVo.getId());
                flow.setCurrency(currency);
                flow.setAmount(amount);
                flow.setAmountType("1"); // 1入账
                flow.setFlowType("4"); // 4退"
                flow.setAmountBefore(walletVo.getAmountLeft());
                flow.setAmountAfter(walletVo.getAmountLeft() + amount);
                flow.setFee(0L);
                flow.setTotalFee(0L);
                flow.setTranTime(new Date());
                flow.setBusinessType("4"); // 4退"
                flow.setBusinessId(businessId);
                flow.setBusinessNo(businessNo);
                flow.setTransactionStatus("1"); // 1成功
                flow.setDescription(description);

                boolean flowResult = walletFlowMapper.insert(flow) > 0;
                if (!flowResult) {
                    throw new ServiceException("创建流水记录失败");
                }


                return true;
            } catch (Exception e) {
                log.error("退款失", e);
                throw new ServiceException("退款失");
            }
        });
    }

    /**
     * 查询钱包余额
     *
     * @param tenantId 租户ID
     * @param currency 币种
     * @return 可用余额（单位：分），如果钱包不存在返回0
     */
    @Override
    public Long queryBalance(String tenantId, String currency) {
        GlobalWalletVo wallet = queryByTenantIdAndCurrency(tenantId, currency);
        if (wallet == null) {

            return 0L;
        }
        return wallet.getAmountLeft();
    }

    /**
     * 创建钱包（如果不存在"
     *
     * @param tenantId 租户ID
     * @param merchantId 商户ID
     * @param currency 币种
     * @return 创建结果，true表示钱包已存在或创建成功，false表示创建失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createWalletIfNotExists(String tenantId, Long merchantId, String currency) {
        // 使用 TenantHelper.dynamic() 设置租户上下文（跨服务调用时租户上下文不会自动传递）
        return TenantHelper.dynamic(tenantId, () -> {
            try {
                // 先查询钱包是否存"
                GlobalWalletVo existWallet = queryByTenantIdAndCurrency(tenantId, currency);
                if (existWallet != null) {

                    return true;
                }

                // 创建钱包
                GlobalWalletBo bo = new GlobalWalletBo();
                bo.setTenantId(tenantId);
                bo.setMerchantId(merchantId);
                bo.setCurrency(currency);

                boolean result = insertByBo(bo);
                if (result) {
                    log.info("创建钱包成功: tenantId={}, merchantId={}, currency={}", tenantId, merchantId, currency);
                } else {
                    log.error("创建钱包失败: tenantId={}, merchantId={}, currency={}", tenantId, merchantId, currency);
                }
                return result;
            } catch (Exception e) {
                log.error("创建钱包异常: tenantId={}, merchantId={}, currency={}", tenantId, merchantId, currency, e);
                return false;
            }
        });
    }

    /**
     * 生成钱包编号
     *
     * @return 钱包编号
     */
    private String generateWalletNo() {
        return "WL" + System.currentTimeMillis() + RandomUtil.randomNumbers(4);
    }
}
