package org.mmsy.product.service.impl;

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
import org.mmsy.product.domain.bo.ProAppPackageBo;
import org.mmsy.product.domain.vo.ProAppPackageVo;
import org.mmsy.product.domain.vo.ProAppVo;
import org.mmsy.product.domain.vo.ProAiAgentVo;
import org.mmsy.product.domain.ProAppPackage;
import org.mmsy.product.domain.ProApp;
import org.mmsy.product.domain.ProAiAgent;
import org.mmsy.product.mapper.ProAppPackageMapper;
import org.mmsy.product.mapper.ProAppMapper;
import org.mmsy.product.mapper.ProAiAgentMapper;
import org.mmsy.product.service.IProAppPackageService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.dromara.common.core.exception.ServiceException;

import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * 套餐管理Service业务层处理（包含应用套餐和AI智能体套餐）
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProAppPackageServiceImpl implements IProAppPackageService {

    private final ProAppPackageMapper baseMapper;
    private final ProAppMapper proAppMapper;
    private final ProAiAgentMapper proAiAgentMapper;

    /**
     * 查询套餐
     *
     * @param id 主键
     * @return 套餐信息
     */
    @Override
    public ProAppPackageVo queryById(Long id){
        ProAppPackageVo vo = baseMapper.selectVoById(id);
        return vo;
    }

    /**
     * 分页查询套餐列表（支持按类型筛选）
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 套餐分页列表
     */
    @Override
    public TableDataInfo<ProAppPackageVo> queryPageList(ProAppPackageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ProAppPackage> lqw = buildQueryWrapper(bo);
        Page<ProAppPackageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (Boolean.TRUE.equals(bo.getIncludeAppIds())) {
            result.getRecords().forEach(this::convertAppIdsToList);
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的套餐列表
     *
     * @param bo 查询条件
     * @return 套餐列表
     */
    @Override
    public List<ProAppPackageVo> queryList(ProAppPackageBo bo) {
        LambdaQueryWrapper<ProAppPackage> lqw = buildQueryWrapper(bo);
        List<ProAppPackageVo> list = baseMapper.selectVoList(lqw);
        if (Boolean.TRUE.equals(bo.getIncludeAppIds())) {
            list.forEach(this::convertAppIdsToList);
        }
        return list;
    }

    private LambdaQueryWrapper<ProAppPackage> buildQueryWrapper(ProAppPackageBo bo) {
        LambdaQueryWrapper<ProAppPackage> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ProAppPackage::getSortOrder);
        lqw.orderByAsc(ProAppPackage::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getPackageCode()), ProAppPackage::getPackageCode, bo.getPackageCode());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), ProAppPackage::getPackageName, bo.getPackageName());
        lqw.eq(StringUtils.isNotBlank(bo.getPackageType()), ProAppPackage::getPackageType, bo.getPackageType());
        lqw.eq(StringUtils.isNotBlank(bo.getAppIds()), ProAppPackage::getAppIds, bo.getAppIds());
        lqw.eq(bo.getMonthPrice() != null, ProAppPackage::getMonthPrice, bo.getMonthPrice());
        lqw.eq(bo.getYearPrice() != null, ProAppPackage::getYearPrice, bo.getYearPrice());
        lqw.eq(bo.getTrialDays() != null, ProAppPackage::getTrialDays, bo.getTrialDays());
        lqw.eq(bo.getMaxUser() != null, ProAppPackage::getMaxUser, bo.getMaxUser());
        lqw.eq(bo.getStatus() != null, ProAppPackage::getStatus, bo.getStatus());
        lqw.eq(bo.getSortOrder() != null, ProAppPackage::getSortOrder, bo.getSortOrder());
        return lqw;
    }

    /**
     * 新增套餐
     *
     * @param bo 套餐信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(ProAppPackageBo bo) {
        ProAppPackage add = MapstructUtils.convert(bo, ProAppPackage.class);
        // 将appIdList 转换为逗号分隔的字符串
        if (CollUtil.isNotEmpty(bo.getAppIdList())) {
            String appIds = bo.getAppIdList().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            add.setAppIds(appIds);
        }
        // 将agentIdList 转换为逗号分隔的字符串
        if (CollUtil.isNotEmpty(bo.getAgentIdList())) {
            String agentIds = bo.getAgentIdList().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            add.setAgentIds(agentIds);
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改套餐
     *
     * @param bo 套餐信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(ProAppPackageBo bo) {
        ProAppPackage update = MapstructUtils.convert(bo, ProAppPackage.class);
        // 将appIdList 转换为逗号分隔的字符串
        if (CollUtil.isNotEmpty(bo.getAppIdList())) {
            String appIds = bo.getAppIdList().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            update.setAppIds(appIds);
        }
        // 将agentIdList 转换为逗号分隔的字符串
        if (CollUtil.isNotEmpty(bo.getAgentIdList())) {
            String agentIds = bo.getAgentIdList().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            update.setAgentIds(agentIds);
        }
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ProAppPackage entity){
        //TODO 做一些数据校验，如唯一约束
    }

    /**
     * 校验并批量删除应用套餐信息
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
     * 将appIds 和agentIds 字符串转换为列表
     *
     * @param vo 视图对象
     */
    private void convertAppIdsToList(ProAppPackageVo vo) {
        if (vo == null) {
            return;
        }
        // 转换应用ID列表
        if (StrUtil.isNotBlank(vo.getAppIds())) {
            List<Long> appIdList = Arrays.stream(vo.getAppIds().split(","))
                .filter(StrUtil::isNotBlank)
                .map(Long::valueOf)
                .collect(Collectors.toList());
            vo.setAppIdList(appIdList);
        }
        // 转换智能体ID列表
        if (StrUtil.isNotBlank(vo.getAgentIds())) {
            List<Long> agentIdList = Arrays.stream(vo.getAgentIds().split(","))
                .filter(StrUtil::isNotBlank)
                .map(Long::valueOf)
                .collect(Collectors.toList());
            vo.setAgentIdList(agentIdList);
        }
    }

    /**
     * 根据套餐ID查询关联的应用列表
     *
     * @param packageId 套餐ID
     * @return 应用列表
     */
    @Override
    public List<ProAppVo> queryAppsByPackageId(Long packageId) {
        log.info("查询套餐关联应用列表，packageId: {}", packageId);

        // 查询套餐信息
        ProAppPackageVo packageVo = baseMapper.selectVoById(packageId);
        if (packageVo == null) {
            throw new ServiceException("套餐不存在");
        }

        // 如果套餐没有关联应用，返回空列表
        if (StrUtil.isBlank(packageVo.getAppIds())) {
            return Collections.emptyList();
        }

        // 解析应用ID列表
        List<Long> appIdList = Arrays.stream(packageVo.getAppIds().split(","))
            .filter(StrUtil::isNotBlank)
            .map(Long::valueOf)
            .collect(Collectors.toList());

        if (CollUtil.isEmpty(appIdList)) {
            return Collections.emptyList();
        }

        // 查询应用列表
        List<ProAppVo> appList = proAppMapper.selectVoList(
            Wrappers.<ProApp>lambdaQuery()
                .in(ProApp::getId, appIdList)
                .orderByAsc(ProApp::getSortOrder)
        );

        return appList;
    }

    /**
     * 根据套餐ID查询关联的AI智能体列表
     *
     * @param packageId 套餐ID
     * @return AI智能体列表
     */
    @Override
    public List<ProAiAgentVo> queryAgentsByPackageId(Long packageId) {
        log.info("查询套餐关联AI智能体列表，packageId: {}", packageId);

        // 查询套餐信息
        ProAppPackageVo packageVo = baseMapper.selectVoById(packageId);
        if (packageVo == null) {
            throw new ServiceException("套餐不存在");
        }

        // 如果套餐没有关联智能体，返回空列表
        if (StrUtil.isBlank(packageVo.getAgentIds())) {
            return Collections.emptyList();
        }

        // 解析智能体ID列表
        List<Long> agentIdList = Arrays.stream(packageVo.getAgentIds().split(","))
            .filter(StrUtil::isNotBlank)
            .map(Long::valueOf)
            .collect(Collectors.toList());

        if (CollUtil.isEmpty(agentIdList)) {
            return Collections.emptyList();
        }

        // 查询智能体列表
        List<ProAiAgentVo> agentList = proAiAgentMapper.selectVoList(
            Wrappers.<ProAiAgent>lambdaQuery()
                .in(ProAiAgent::getId, agentIdList)
                .orderByAsc(ProAiAgent::getSortOrder)
        );

        return agentList;
    }

    /**
     * 根据套餐编码查询套餐信息
     *
     * @param packageCode 套餐编码
     * @return 套餐信息
     */
    @Override
    public ProAppPackageVo queryByPackageCode(String packageCode) {
        log.info("根据套餐编码查询套餐，packageCode: {}", packageCode);
        if (StrUtil.isBlank(packageCode)) {
            return null;
        }
        LambdaQueryWrapper<ProAppPackage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProAppPackage::getPackageCode, packageCode);
        wrapper.last("LIMIT 1");
        return baseMapper.selectVoOne(wrapper);
    }

    /**
     * 修改套餐状态
     *
     * @param id     套餐ID
     * @param status 状态（1-启用，0-禁用）
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatus(Long id, Long status) {
        log.info("修改套餐状态，id: {}, status: {}", id, status);

        // 校验套餐是否存在
        ProAppPackageVo packageVo = baseMapper.selectVoById(id);
        if (packageVo == null) {
            throw new ServiceException("套餐不存在");
        }

        // 校验状态值
        if (status == null || (status != 0L && status != 1L)) {
            throw new ServiceException("状态值不合法，只能为0或1");
        }

        // 更新状态
        ProAppPackage update = new ProAppPackage();
        update.setId(id);
        update.setStatus(status);

        boolean result = baseMapper.updateById(update) > 0;
        log.info("套餐状态修改{}，id: {}, status: {}", result ? "成功" : "失败", id, status);
        return result;
    }

}
