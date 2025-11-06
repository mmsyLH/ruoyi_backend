package org.mmsy.merchant.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.mmsy.merchant.domain.bo.GlobalWalletFlowBo;
import org.mmsy.merchant.domain.vo.GlobalWalletFlowVo;
import org.mmsy.merchant.domain.GlobalWalletFlow;
import org.mmsy.merchant.mapper.GlobalWalletFlowMapper;
import org.mmsy.merchant.service.IGlobalWalletFlowService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 全球钱包流水Service业务层处"
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GlobalWalletFlowServiceImpl implements IGlobalWalletFlowService {

    private final GlobalWalletFlowMapper baseMapper;

    /**
     * 查询全球钱包流水
     *
     * @param id 主键
     * @return 全球钱包流水
     */
    @Override
    public GlobalWalletFlowVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询全球钱包流水列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 全球钱包流水分页列表
     */
    @Override
    public TableDataInfo<GlobalWalletFlowVo> queryPageList(GlobalWalletFlowBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<GlobalWalletFlow> lqw = buildQueryWrapper(bo);
        Page<GlobalWalletFlowVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的全球钱包流水列"
     *
     * @param bo 查询条件
     * @return 全球钱包流水列表
     */
    @Override
    public List<GlobalWalletFlowVo> queryList(GlobalWalletFlowBo bo) {
        LambdaQueryWrapper<GlobalWalletFlow> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<GlobalWalletFlow> buildQueryWrapper(GlobalWalletFlowBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<GlobalWalletFlow> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(GlobalWalletFlow::getId);
        lqw.eq(bo.getMerchantId() != null, GlobalWalletFlow::getMerchantId, bo.getMerchantId());
        lqw.eq(bo.getWalletId() != null, GlobalWalletFlow::getWalletId, bo.getWalletId());
        lqw.eq(StringUtils.isNotBlank(bo.getCurrency()), GlobalWalletFlow::getCurrency, bo.getCurrency());
        lqw.eq(bo.getAmount() != null, GlobalWalletFlow::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getAmountType()), GlobalWalletFlow::getAmountType, bo.getAmountType());
        lqw.eq(StringUtils.isNotBlank(bo.getFlowType()), GlobalWalletFlow::getFlowType, bo.getFlowType());
        lqw.eq(bo.getAmountBefore() != null, GlobalWalletFlow::getAmountBefore, bo.getAmountBefore());
        lqw.eq(bo.getAmountAfter() != null, GlobalWalletFlow::getAmountAfter, bo.getAmountAfter());
        lqw.eq(bo.getFee() != null, GlobalWalletFlow::getFee, bo.getFee());
        lqw.eq(bo.getTotalFee() != null, GlobalWalletFlow::getTotalFee, bo.getTotalFee());
        lqw.eq(bo.getTranTime() != null, GlobalWalletFlow::getTranTime, bo.getTranTime());
        lqw.eq(StringUtils.isNotBlank(bo.getBusinessType()), GlobalWalletFlow::getBusinessType, bo.getBusinessType());
        lqw.eq(bo.getBusinessId() != null, GlobalWalletFlow::getBusinessId, bo.getBusinessId());
        lqw.eq(StringUtils.isNotBlank(bo.getBusinessNo()), GlobalWalletFlow::getBusinessNo, bo.getBusinessNo());
        lqw.eq(StringUtils.isNotBlank(bo.getChannelCode()), GlobalWalletFlow::getChannelCode, bo.getChannelCode());
        lqw.eq(StringUtils.isNotBlank(bo.getTransactionId()), GlobalWalletFlow::getTransactionId, bo.getTransactionId());
        lqw.eq(StringUtils.isNotBlank(bo.getTransactionStatus()), GlobalWalletFlow::getTransactionStatus, bo.getTransactionStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getCounterParty()), GlobalWalletFlow::getCounterParty, bo.getCounterParty());
        lqw.eq(StringUtils.isNotBlank(bo.getToEmail()), GlobalWalletFlow::getToEmail, bo.getToEmail());
        lqw.eq(bo.getOppositeWalletId() != null, GlobalWalletFlow::getOppositeWalletId, bo.getOppositeWalletId());
        lqw.eq(bo.getOppositeMerchantId() != null, GlobalWalletFlow::getOppositeMerchantId, bo.getOppositeMerchantId());
        lqw.eq(StringUtils.isNotBlank(bo.getOppositeAccount()), GlobalWalletFlow::getOppositeAccount, bo.getOppositeAccount());
        lqw.like(StringUtils.isNotBlank(bo.getOppositeName()), GlobalWalletFlow::getOppositeName, bo.getOppositeName());
        lqw.eq(StringUtils.isNotBlank(bo.getFromAddress()), GlobalWalletFlow::getFromAddress, bo.getFromAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getToAddress()), GlobalWalletFlow::getToAddress, bo.getToAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getTxHash()), GlobalWalletFlow::getTxHash, bo.getTxHash());
        lqw.eq(StringUtils.isNotBlank(bo.getNetworkType()), GlobalWalletFlow::getNetworkType, bo.getNetworkType());
        lqw.eq(StringUtils.isNotBlank(bo.getTranMsg()), GlobalWalletFlow::getTranMsg, bo.getTranMsg());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), GlobalWalletFlow::getDescription, bo.getDescription());
        return lqw;
    }

    /**
     * 新增全球钱包流水
     *
     * @param bo 全球钱包流水
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(GlobalWalletFlowBo bo) {
        GlobalWalletFlow add = MapstructUtils.convert(bo, GlobalWalletFlow.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改全球钱包流水
     *
     * @param bo 全球钱包流水
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(GlobalWalletFlowBo bo) {
        GlobalWalletFlow update = MapstructUtils.convert(bo, GlobalWalletFlow.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(GlobalWalletFlow entity){
        //TODO 做一些数据校"如唯一约束
    }

    /**
     * 校验并批量删除全球钱包流水信"
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
}
