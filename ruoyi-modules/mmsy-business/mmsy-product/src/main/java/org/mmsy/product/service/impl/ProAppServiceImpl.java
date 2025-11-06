package org.mmsy.product.service.impl;


import cn.hutool.json.JSONObject;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.service.MerchantService;
import org.dromara.common.core.utils.MapstructUtils;
import org.mmsy.merchant.domain.vo.MerMerchantVo;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import org.mmsy.product.domain.bo.ProAppBo;
import org.mmsy.product.domain.vo.ProAppVo;
import org.mmsy.product.domain.ProApp;
import org.mmsy.product.mapper.ProAppMapper;
import org.mmsy.product.service.IProAppService;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 中控平台应用Service业务层处理
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProAppServiceImpl implements IProAppService {

    private final ProAppMapper baseMapper;
    private final MerchantService merchantService;

    /**
     * Redis缓存Key前缀：应用Token（使用全局前缀绕过租户隔离）
     */
    private static final String CACHE_KEY_PREFIX = GlobalConstants.GLOBAL_REDIS_KEY + "product:app:token:";

    /**
     * 查询中控平台应用
     *
     * @param id 主键
     * @return 中控平台应用
     */
    @Override
    public ProAppVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询中控平台应用列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 中控平台应用分页列表
     */
    @Override
    public TableDataInfo<ProAppVo> queryPageList(ProAppBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProApp> lqw = buildQueryWrapper(bo);
        Page<ProAppVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的中控平台应用列表
     *
     * @param bo 查询条件
     * @return 中控平台应用列表
     */
    @Override
    public List<ProAppVo> queryList(ProAppBo bo) {
        LambdaQueryWrapper<ProApp> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ProApp> buildQueryWrapper(ProAppBo bo) {
        LambdaQueryWrapper<ProApp> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ProApp::getSortOrder).orderByAsc(ProApp::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getAppCode()), ProApp::getAppCode, bo.getAppCode());
        lqw.like(StringUtils.isNotBlank(bo.getAppName()), ProApp::getAppName, bo.getAppName());
        lqw.eq(StringUtils.isNotBlank(bo.getAppDesc()), ProApp::getAppDesc, bo.getAppDesc());
        lqw.eq(StringUtils.isNotBlank(bo.getAppDetail()), ProApp::getAppDetail, bo.getAppDetail());
        lqw.eq(StringUtils.isNotBlank(bo.getFeatures()), ProApp::getFeatures, bo.getFeatures());
        lqw.eq(StringUtils.isNotBlank(bo.getAppType()), ProApp::getAppType, bo.getAppType());
        lqw.eq(bo.getAppCategory() != null, ProApp::getAppCategory, bo.getAppCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getLoginUrl()), ProApp::getLoginUrl, bo.getLoginUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDemoUrl()), ProApp::getDemoUrl, bo.getDemoUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getConfig()), ProApp::getConfig, bo.getConfig());
        lqw.eq(bo.getStatus() != null, ProApp::getStatus, bo.getStatus());
        lqw.eq(bo.getSortOrder() != null, ProApp::getSortOrder, bo.getSortOrder());
        lqw.eq(StringUtils.isNotBlank(bo.getUpdateLog()), ProApp::getUpdateLog, bo.getUpdateLog());
        return lqw;
    }

    /**
     * 新增中控平台应用
     *
     * @param bo 中控平台应用
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ProAppBo bo) {
        ProApp add = MapstructUtils.convert(bo, ProApp.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改中控平台应用
     *
     * @param bo 中控平台应用
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ProAppBo bo) {
        ProApp update = MapstructUtils.convert(bo, ProApp.class);
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;

        // 修改成功后，清除该应用的所有Token缓存
        if (flag && bo.getId() != null) {
            clearAppTokenCache(bo.getId());
        }

        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ProApp entity){
        //TODO 做一些数据校验，如唯一约束
    }

    /**
     * 校验并批量删除中控平台应用信息
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
        boolean flag = baseMapper.deleteByIds(ids) > 0;

        // 删除成功后，清除这些应用的所有Token缓存
        if (flag && ids != null && !ids.isEmpty()) {
            ids.forEach(this::clearAppTokenCache);
        }

        return flag;
    }

    /**
     * 修改中控平台应用状态
     *
     * @param id     主键
     * @param status 状态：1 启用，0 禁用
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatus(Long id, Long status) {
        boolean flag = baseMapper.update(null,
            new LambdaUpdateWrapper<ProApp>()
                .set(ProApp::getStatus, status)
                .eq(ProApp::getId, id)) > 0;

        // 修改状态成功后，清除该应用的所有Token缓存
        if (flag && id != null) {
            clearAppTokenCache(id);
        }

        return flag;
    }

    /**
     * 清除应用的所有Token缓存
     * <p>
     * 当应用被修改、删除或状态变更时，需要清除该应用的所有Token缓存，
     * 避免用户获取到过期的应用信息。
     *
     * @param appId 应用ID
     */
    private void clearAppTokenCache(Long appId) {
        try {
            // 根据appId查询appCode
            ProAppVo appVo = baseMapper.selectVoById(appId);
            if (appVo != null && StringUtils.isNotBlank(appVo.getAppCode())) {
                String pattern = CACHE_KEY_PREFIX + appVo.getAppCode() + ":*";
                RedisUtils.deleteKeys(pattern);
                log.info("清除应用Token缓存成功，appId: {}, appCode: {}, pattern: {}", appId, appVo.getAppCode(), pattern);
            } else {
                log.warn("未找到应用或应用编码为空，无法清除缓存，appId: {}", appId);
            }
        } catch (Exception e) {
            log.error("清除应用Token缓存异常，appId: {}", appId, e);
        }
    }

    /**
     * 获取应用Token（带缓存）
     * <p>
     * 缓存策略：使用Redis缓存Token，缓存时间为Token过期时间的一半，
     * 确保在Token真正过期前自动刷新，提升用户体验。
     *
     * @param appId 应用ID
     * @return Token信息
     */
    @Override
    public R<JSONObject> getAppToken(Long appId) {
    return null;
    }

    /**
     * 根据当前租户权限查询有权限的应用列表（分页）
     * <p>
     * 查询逻辑：
     * 1. 通过当前租户ID查询对应的会员ID
     * 2. 根据会员ID查询有权限的应用编码列表
     * 3. 根据应用编码列表查询应用详情
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 有权限的应用分页列表
     */
    @Override
    public TableDataInfo<ProAppVo> queryAuthorizedAppList(ProAppBo bo, PageQuery pageQuery) {
        try {
            // 1. 获取当前租户ID并查询会员信息
            String tenantId = LoginHelper.getTenantId();
            log.info("查询租户授权应用列表, tenantId: {}", tenantId);

            MerMerchantVo merchantVo = (MerMerchantVo) merchantService.queryByTenantId(tenantId);
            if (merchantVo == null) {
                log.warn("未找到租户对应的会员信息, tenantId: {}", tenantId);
                return TableDataInfo.build(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0));
            }

            // 2. 根据会员ID查询有权限的应用编码列表
            List<String> authorizedAppCodes = merchantService.queryAuthorizedAppCodes(merchantVo.getId());
            if (authorizedAppCodes == null || authorizedAppCodes.isEmpty()) {
                log.info("会员无任何应用权限, memberId: {}", merchantVo.getId());
                return TableDataInfo.build(new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0));
            }

            log.info("查询到会员授权应用编码, memberId: {}, appCodes: {}", merchantVo.getId(), authorizedAppCodes);

            // 3. 构建查询条件：应用编码在授权列表中 + 其他查询条件
            LambdaQueryWrapper<ProApp> lqw = Wrappers.lambdaQuery();
            lqw.in(ProApp::getAppCode, authorizedAppCodes);
            lqw.orderByAsc(ProApp::getSortOrder).orderByAsc(ProApp::getId);

            // 添加其他查询条件
            lqw.like(StringUtils.isNotBlank(bo.getAppName()), ProApp::getAppName, bo.getAppName());
            lqw.eq(StringUtils.isNotBlank(bo.getAppType()), ProApp::getAppType, bo.getAppType());
            lqw.eq(StringUtils.isNotBlank(bo.getAppCategory()), ProApp::getAppCategory, bo.getAppCategory());
            lqw.eq(bo.getStatus() != null, ProApp::getStatus, bo.getStatus());

            // 4. 分页查询
            Page<ProAppVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            log.info("查询授权应用成功, memberId: {}, total: {}", merchantVo.getId(), result.getTotal());

            return TableDataInfo.build(result);

        } catch (Exception e) {
            log.error("查询租户授权应用列表异常", e);
            throw new ServiceException("查询授权应用列表失败: " + e.getMessage());
        }
    }
}

