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
import org.mmsy.merchant.domain.bo.MerMerchantAppPermissionBo;
import org.mmsy.merchant.domain.vo.MerMerchantAppPermissionVo;
import org.mmsy.merchant.domain.MerMerchantAppPermission;
import org.mmsy.merchant.mapper.MerMerchantAppPermissionMapper;
import org.mmsy.merchant.service.IMerMerchantAppPermissionService;

import java.util.List;
import java.util.Collection;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）Service业务层处"
 *
 * @author luohan
 * @date 2025-11-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MerMerchantAppPermissionServiceImpl implements IMerMerchantAppPermissionService {

    private final MerMerchantAppPermissionMapper baseMapper;

    /**
     * 查询会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param id 主键
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）
     */
    @Override
    public MerMerchantAppPermissionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员应用权限（买了哪个套餐、到期时间、用量）列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）分页列表
     */
    @Override
    public TableDataInfo<MerMerchantAppPermissionVo> queryPageList(MerMerchantAppPermissionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MerMerchantAppPermission> lqw = buildQueryWrapper(bo);
        Page<MerMerchantAppPermissionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员应用权限（买了哪个套餐、到期时间、用量）列表
     *
     * @param bo 查询条件
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）列表
     */
    @Override
    public List<MerMerchantAppPermissionVo> queryList(MerMerchantAppPermissionBo bo) {
        LambdaQueryWrapper<MerMerchantAppPermission> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MerMerchantAppPermission> buildQueryWrapper(MerMerchantAppPermissionBo bo) {
        LambdaQueryWrapper<MerMerchantAppPermission> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(MerMerchantAppPermission::getId);
        lqw.eq(bo.getMemberId() != null, MerMerchantAppPermission::getMemberId, bo.getMemberId());
        lqw.eq(StringUtils.isNotBlank(bo.getAppCode()), MerMerchantAppPermission::getAppCode, bo.getAppCode());
        lqw.eq(bo.getPackageId() != null, MerMerchantAppPermission::getPackageId, bo.getPackageId());
        lqw.eq(bo.getExpireAt() != null, MerMerchantAppPermission::getExpireAt, bo.getExpireAt());
        lqw.eq(bo.getUserCntUsed() != null, MerMerchantAppPermission::getUserCntUsed, bo.getUserCntUsed());
        lqw.eq(bo.getUserCntLimit() != null, MerMerchantAppPermission::getUserCntLimit, bo.getUserCntLimit());
        lqw.eq(bo.getStatus() != null, MerMerchantAppPermission::getStatus, bo.getStatus());
        lqw.eq(bo.getAutoRenew() != null, MerMerchantAppPermission::getAutoRenew, bo.getAutoRenew());
        return lqw;
    }

    /**
     * 新增会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param bo 会员应用权限（买了哪个套餐、到期时间、用量）
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MerMerchantAppPermissionBo bo) {
        MerMerchantAppPermission add = MapstructUtils.convert(bo, MerMerchantAppPermission.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param bo 会员应用权限（买了哪个套餐、到期时间、用量）
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MerMerchantAppPermissionBo bo) {
        MerMerchantAppPermission update = MapstructUtils.convert(bo, MerMerchantAppPermission.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MerMerchantAppPermission entity){
        //TODO 做一些数据校"如唯一约束
    }

    /**
     * 校验并批量删除会员应用权限（买了哪个套餐、到期时间、用量）信息
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
     * 授权或续费会员应用权"
     *
     * @param memberId 会员ID
     * @param appCode 应用编码或智能体编码
     * @param itemType 权限项类型：app-应用, ai_agent-AI智能"
     * @param packageId 套餐ID
     * @param months 授权月数
     * @param userCntLimit 子账号上"
     * @return 是否授权成功
     */
    @Override
    public Boolean grantOrRenewPermission(Long memberId, String appCode, String itemType, Long packageId, Integer months, Long userCntLimit) {


        // 查询是否已有权限记录（会员ID + 应用编码 + 权限项类型唯一确定一条记录）
        LambdaQueryWrapper<MerMerchantAppPermission> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MerMerchantAppPermission::getMemberId, memberId);
        wrapper.eq(MerMerchantAppPermission::getAppCode, appCode);
        wrapper.eq(MerMerchantAppPermission::getItemType, itemType);
        MerMerchantAppPermission existingPermission = baseMapper.selectOne(wrapper);

        java.util.Date now = new java.util.Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(java.util.Calendar.MONTH, months);
        java.util.Date newExpireDate = calendar.getTime();

        if (existingPermission != null) {
            // 已有权限记录，续费（延长过期时间"


            // 如果原过期时间在当前时间之后，从原过期时间开始延长；否则从当前时间开始延"
            calendar.setTime(existingPermission.getExpireAt().after(now) ? existingPermission.getExpireAt() : now);
            calendar.add(java.util.Calendar.MONTH, months);
            java.util.Date extendedExpireDate = calendar.getTime();

            existingPermission.setPackageId(packageId);
            existingPermission.setExpireAt(extendedExpireDate);
            existingPermission.setUserCntLimit(userCntLimit);
            existingPermission.setStatus(1L); // 启用

            boolean result = baseMapper.updateById(existingPermission) > 0;

            return result;
        } else {
            // 新增权限记录
            log.info("会员无应用权限，执行新增操作");
            MerMerchantAppPermission newPermission = new MerMerchantAppPermission();
            newPermission.setMemberId(memberId);
            newPermission.setAppCode(appCode);
            newPermission.setItemType(itemType); // 从参数获取权限项类型
            newPermission.setPackageId(packageId);
            newPermission.setExpireAt(newExpireDate);
            newPermission.setUserCntUsed(0L);
            newPermission.setUserCntLimit(userCntLimit);
            newPermission.setStatus(1L); // 启用
            newPermission.setAutoRenew(0L); // 默认不自动续"

            boolean result = baseMapper.insert(newPermission) > 0;
            log.info("新增应用权限{}, 过期时间: {}, 权限类型: {}", result ? "成功" : "失败", newExpireDate, itemType);
            return result;
        }
    }
}

