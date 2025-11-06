package org.mmsy.merchant.service;

import org.mmsy.merchant.domain.vo.MerMerchantAppPermissionVo;
import org.mmsy.merchant.domain.bo.MerMerchantAppPermissionBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员应用权限（买了哪个套餐、到期时间、用量）Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IMerMerchantAppPermissionService {

    /**
     * 查询会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param id 主键
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）
     */
    MerMerchantAppPermissionVo queryById(Long id);

    /**
     * 分页查询会员应用权限（买了哪个套餐、到期时间、用量）列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）分页列表
     */
    TableDataInfo<MerMerchantAppPermissionVo> queryPageList(MerMerchantAppPermissionBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员应用权限（买了哪个套餐、到期时间、用量）列表
     *
     * @param bo 查询条件
     * @return 会员应用权限（买了哪个套餐、到期时间、用量）列表
     */
    List<MerMerchantAppPermissionVo> queryList(MerMerchantAppPermissionBo bo);

    /**
     * 新增会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param bo 会员应用权限（买了哪个套餐、到期时间、用量）
     * @return 是否新增成功
     */
    Boolean insertByBo(MerMerchantAppPermissionBo bo);

    /**
     * 修改会员应用权限（买了哪个套餐、到期时间、用量）
     *
     * @param bo 会员应用权限（买了哪个套餐、到期时间、用量）
     * @return 是否修改成功
     */
    Boolean updateByBo(MerMerchantAppPermissionBo bo);

    /**
     * 校验并批量删除会员应用权限（买了哪个套餐、到期时间、用量）信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校"
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

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
    Boolean grantOrRenewPermission(Long memberId, String appCode, String itemType, Long packageId, Integer months, Long userCntLimit);
}

