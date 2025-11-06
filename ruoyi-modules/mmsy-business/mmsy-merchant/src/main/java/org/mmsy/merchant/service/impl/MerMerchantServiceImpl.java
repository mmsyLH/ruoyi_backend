package org.mmsy.merchant.service.impl;

import org.dromara.common.core.service.MerchantService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.mmsy.merchant.domain.bo.MerMerchantBo;
import org.mmsy.merchant.domain.vo.MerMerchantVo;
import org.mmsy.merchant.domain.MerMerchant;
import org.mmsy.merchant.mapper.MerMerchantMapper;
import org.mmsy.merchant.service.IMerMerchantService;


import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 会员/租户Service业务层处理
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MerMerchantServiceImpl implements IMerMerchantService {

    private final MerMerchantMapper baseMapper;
//    private final IProAppPackageService appPackageService;

    /**
     * 查询会员/租户
     *
     * @param id 主键
     * @return 会员/租户
     */
    @Override
    public MerMerchantVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员/租户列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员/租户分页列表
     */
    @Override
    public TableDataInfo<MerMerchantVo> queryPageList(MerMerchantBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MerMerchant> lqw = buildQueryWrapper(bo);
        Page<MerMerchantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员/租户列表
     *
     * @param bo 查询条件
     * @return 会员/租户列表
     */
    @Override
    public List<MerMerchantVo> queryList(MerMerchantBo bo) {
        LambdaQueryWrapper<MerMerchant> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MerMerchant> buildQueryWrapper(MerMerchantBo bo) {
        LambdaQueryWrapper<MerMerchant> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(MerMerchant::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getMemberCode()), MerMerchant::getMemberCode, bo.getMemberCode());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), MerMerchant::getCompanyName, bo.getCompanyName());
        lqw.like(StringUtils.isNotBlank(bo.getContactName()), MerMerchant::getContactName, bo.getContactName());
        lqw.eq(StringUtils.isNotBlank(bo.getMobile()), MerMerchant::getMobile, bo.getMobile());
        lqw.eq(StringUtils.isNotBlank(bo.getEmail()), MerMerchant::getEmail, bo.getEmail());
        lqw.eq(bo.getStatus() != null, MerMerchant::getStatus, bo.getStatus());
        lqw.eq(bo.getRegisterAt() != null, MerMerchant::getRegisterAt, bo.getRegisterAt());
        lqw.eq(bo.getExpireAt() != null, MerMerchant::getExpireAt, bo.getExpireAt());
        lqw.eq(bo.getAppPackageId() != null, MerMerchant::getAppPackageId, bo.getAppPackageId());
        return lqw;
    }

    /**
     * 新增会员/租户
     *
     * @param bo 会员/租户
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MerMerchantBo bo) {
        MerMerchant add = MapstructUtils.convert(bo, MerMerchant.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 新增会员/租户并返回ID
     *
     * @param bo 会员/租户
     * @return 新增的会员ID，失败返回null
     */
    @Override
    public Long insertByBoAndReturnId(MerMerchantBo bo) {
        MerMerchant add = MapstructUtils.convert(bo, MerMerchant.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if (add != null) {
                return add.getId();
            }
        }
        return null;
    }

    /**
     * 修改会员/租户
     *
     * @param bo 会员/租户
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MerMerchantBo bo) {
        MerMerchant update = MapstructUtils.convert(bo, MerMerchant.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MerMerchant entity){
        //TODO 做一些数据校验，如唯一约束
    }

    /**
     * 校验并批量删除会员/租户信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验，判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 修改会员/租户状态
     *
     * @param id     主键
     * @param status 状态：1 正常，0 禁用
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatus(Long id, Long status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<MerMerchant>()
                .set(MerMerchant::getStatus, status)
                .eq(MerMerchant::getId, id)) > 0;
    }

    /**
     * 根据租户ID查询会员信息
     *
     * @param tenantId 租户编号
     * @return 会员/租户
     */
    @Override
    public MerMerchantVo queryByTenantId(String tenantId) {
        LambdaQueryWrapper<MerMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(tenantId), MerMerchant::getTenantId, tenantId);
        wrapper.last("LIMIT 1");
        return baseMapper.selectVoOne(wrapper);
    }

    /**
     * 根据会员ID查询有权限的应用编码列表
     *
     * @param merchantId 会员ID
     * @return 应用编码列表
     */
    @Override
    public List<String> queryAuthorizedAppCodes(Long merchantId) {
        try {
            // 1. 查询会员信息，获取套餐ID
            MerMerchantVo merchantVo = baseMapper.selectVoById(merchantId);
            if (merchantVo == null || merchantVo.getAppPackageId() == null) {
                log.warn("会员不存在或未关联套餐, merchantId: {}", merchantId);
                return new ArrayList<>();
            }

//            // 2. 查询套餐信息，获取应用ID列表
//            ProAppPackageVo packageVo = appPackageService.queryById(merchantVo.getAppPackageId());
//            if (packageVo == null) {
//                log.warn("套餐不存在, packageId: {}", merchantVo.getAppPackageId());
//                return new ArrayList<>();
//            }
//
//            // 3. 从套餐关联的应用列表中获取应用编码
//            List<String> appCodes = new ArrayList<>();
//            if (packageVo.getAppList() != null && !packageVo.getAppList().isEmpty()) {
//                packageVo.getAppList().forEach(app -> {
//                    if (app != null && StringUtils.isNotBlank(app.getAppCode())) {
//                        appCodes.add(app.getAppCode());
//                    }
//                });
//            }
//
//            log.info("查询会员授权应用编码成功, merchantId: {}, packageId: {}, appCodes: {}",
//                merchantId, merchantVo.getAppPackageId(), appCodes);
//            return appCodes;
            return null;
        } catch (Exception e) {
            log.error("查询会员授权应用编码异常, merchantId: {}", merchantId, e);
            return new ArrayList<>();
        }
    }

    /**
     * 根据会员ID查询有权限的AI智能体编码列表
     *
     * @param merchantId 会员ID
     * @return AI智能体编码列表
     */
    @Override
    public List<String> queryAuthorizedAiAgentCodes(Long merchantId) {
        try {
            // 1. 查询会员信息，获取套餐ID
            MerMerchantVo merchantVo = baseMapper.selectVoById(merchantId);
            if (merchantVo == null || merchantVo.getAppPackageId() == null) {
                log.warn("会员不存在或未关联套餐, merchantId: {}", merchantId);
                return new ArrayList<>();
            }
//
//            // 2. 查询套餐信息，获取AI智能体ID列表
//            ProAppPackageVo packageVo = appPackageService.queryById(merchantVo.getAppPackageId());
//            if (packageVo == null) {
//                log.warn("套餐不存在, packageId: {}", merchantVo.getAppPackageId());
//                return new ArrayList<>();
//            }
//
//            // 3. 从套餐关联的AI智能体列表中获取编码
//            List<String> agentCodes = new ArrayList<>();
//            if (packageVo.getAgentList() != null && !packageVo.getAgentList().isEmpty()) {
//                packageVo.getAgentList().forEach(agent -> {
//                    if (agent != null && StringUtils.isNotBlank(agent.getAgentCode())) {
//                        agentCodes.add(agent.getAgentCode());
//                    }
//                });
//            }
//
//            log.info("查询会员授权AI智能体编码成功, merchantId: {}, packageId: {}, agentCodes: {}",
//                merchantId, merchantVo.getAppPackageId(), agentCodes);
//            return agentCodes;
            return null;
        } catch (Exception e) {
            log.error("查询会员授权AI智能体编码异常, merchantId: {}", merchantId, e);
            return new ArrayList<>();
        }
    }
}

