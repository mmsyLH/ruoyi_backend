package org.mmsy.product.service.impl;

import org.dromara.common.core.service.MerchantService;
import org.dromara.common.core.utils.MapstructUtils;
import org.mmsy.merchant.domain.vo.MerMerchantVo;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.satoken.utils.LoginHelper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.mmsy.product.domain.bo.ProAiAgentBo;

import org.mmsy.product.domain.vo.ProAiAgentVo;

import org.mmsy.product.domain.ProAiAgent;

import org.mmsy.product.mapper.ProAiAgentMapper;

import org.mmsy.product.service.IProAiAgentService;


import java.util.List;

import java.util.Collection;


/**
 * AI智能体Service业务层处理
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProAiAgentServiceImpl implements IProAiAgentService {

    private final ProAiAgentMapper baseMapper;
    private final MerchantService merchantService;

    /**
     * 查询AI智能体
     *
     * @param id 主键
     * @return AI智能体
     */
    @Override
    public ProAiAgentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询AI智能体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return AI智能体分页列表
     */
    @Override
    public TableDataInfo<ProAiAgentVo> queryPageList(ProAiAgentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProAiAgent> lqw = buildQueryWrapper(bo);
        Page<ProAiAgentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的AI智能体列表
     *
     * @param bo 查询条件
     * @return AI智能体列表
     */
    @Override
    public List<ProAiAgentVo> queryList(ProAiAgentBo bo) {
        LambdaQueryWrapper<ProAiAgent> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProAiAgent> buildQueryWrapper(ProAiAgentBo bo) {
        LambdaQueryWrapper<ProAiAgent> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ProAiAgent::getSortOrder);
        lqw.orderByAsc(ProAiAgent::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getAgentCode()), ProAiAgent::getAgentCode, bo.getAgentCode());
        lqw.like(StringUtils.isNotBlank(bo.getAgentName()), ProAiAgent::getAgentName, bo.getAgentName());
        lqw.eq(StringUtils.isNotBlank(bo.getAgentDesc()), ProAiAgent::getAgentDesc, bo.getAgentDesc());
        lqw.eq(StringUtils.isNotBlank(bo.getAgentDetail()), ProAiAgent::getAgentDetail, bo.getAgentDetail());
        lqw.eq(StringUtils.isNotBlank(bo.getFeatures()), ProAiAgent::getFeatures, bo.getFeatures());
        lqw.eq(bo.getAgentIcon() != null, ProAiAgent::getAgentIcon, bo.getAgentIcon());
        lqw.eq(StringUtils.isNotBlank(bo.getAgentType()), ProAiAgent::getAgentType, bo.getAgentType());
        lqw.eq(StringUtils.isNotBlank(bo.getAgentCategory()), ProAiAgent::getAgentCategory, bo.getAgentCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getAccessUrl()), ProAiAgent::getAccessUrl, bo.getAccessUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDemoUrl()), ProAiAgent::getDemoUrl, bo.getDemoUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getModelConfig()), ProAiAgent::getModelConfig, bo.getModelConfig());
        lqw.eq(StringUtils.isNotBlank(bo.getPromptTemplate()), ProAiAgent::getPromptTemplate, bo.getPromptTemplate());
        lqw.eq(StringUtils.isNotBlank(bo.getKnowledgeBaseIds()), ProAiAgent::getKnowledgeBaseIds, bo.getKnowledgeBaseIds());
        lqw.eq(StringUtils.isNotBlank(bo.getConfig()), ProAiAgent::getConfig, bo.getConfig());
        lqw.eq(bo.getStatus() != null, ProAiAgent::getStatus, bo.getStatus());
        lqw.eq(bo.getSortOrder() != null, ProAiAgent::getSortOrder, bo.getSortOrder());
        lqw.eq(StringUtils.isNotBlank(bo.getUpdateLog()), ProAiAgent::getUpdateLog, bo.getUpdateLog());
        return lqw;
    }

    /**
     * 新增AI智能体
     *
     * @param bo AI智能体
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ProAiAgentBo bo) {
        ProAiAgent add = MapstructUtils.convert(bo, ProAiAgent.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改AI智能体
     *
     * @param bo AI智能体
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ProAiAgentBo bo) {
        ProAiAgent update = MapstructUtils.convert(bo, ProAiAgent.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ProAiAgent entity){
        //TODO 做一些数据校验，如唯一约束
    }

    /**
     * 校验并批量删除AI智能体信息
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
     * 修改AI智能体状态
     *
     * @param id     主键
     * @param status 状态：1 启用，0 禁用
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatus(Long id, Long status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<ProAiAgent>()
                .set(ProAiAgent::getStatus, status)
                .eq(ProAiAgent::getId, id)) > 0;
    }

    /**
     * 根据当前租户权限查询有权限的AI智能体列表（分页）
     * <p>
     * 查询逻辑：
     * 1. 通过当前租户ID查询对应的会员ID
     * 2. 根据会员ID查询有权限的AI智能体编码列表
     * 3. 根据AI智能体编码列表查询AI智能体详情
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 有权限的AI智能体分页列表
     */
    @Override
    public TableDataInfo<ProAiAgentVo> queryAuthorizedAiAgentList(ProAiAgentBo bo, PageQuery pageQuery) {
            // 1. 获取当前租户ID并查询会员信息
            String tenantId = LoginHelper.getTenantId();
            MerMerchantVo merchantVo = (MerMerchantVo) merchantService.queryByTenantId(tenantId);
            if (merchantVo == null) {
                log.warn("未找到租户对应的会员信息, tenantId: {}", tenantId);
                return TableDataInfo.build(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0));
            }

            // 2. 根据会员ID查询有权限的AI智能体编码列表
            List<String> authorizedAgentCodes = merchantService.queryAuthorizedAiAgentCodes(merchantVo.getId());
            if (authorizedAgentCodes == null || authorizedAgentCodes.isEmpty()) {
                return TableDataInfo.build(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0));
            }

            // 3. 构建查询条件：AI智能体编码在授权列表中 + 其他查询条件
            LambdaQueryWrapper<ProAiAgent> lqw = Wrappers.lambdaQuery();
            lqw.in(ProAiAgent::getAgentCode, authorizedAgentCodes);
            lqw.orderByAsc(ProAiAgent::getSortOrder).orderByAsc(ProAiAgent::getId);

            // 添加其他查询条件
            lqw.like(StringUtils.isNotBlank(bo.getAgentName()), ProAiAgent::getAgentName, bo.getAgentName());
            lqw.eq(StringUtils.isNotBlank(bo.getAgentType()), ProAiAgent::getAgentType, bo.getAgentType());
            lqw.eq(StringUtils.isNotBlank(bo.getAgentCategory()), ProAiAgent::getAgentCategory, bo.getAgentCategory());
            lqw.eq(bo.getStatus() != null, ProAiAgent::getStatus, bo.getStatus());

            // 4. 分页查询
            Page<ProAiAgentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            log.info("查询授权AI智能体成功, memberId: {}, total: {}", merchantVo.getId(), result.getTotal());

            return TableDataInfo.build(result);
    }

}
