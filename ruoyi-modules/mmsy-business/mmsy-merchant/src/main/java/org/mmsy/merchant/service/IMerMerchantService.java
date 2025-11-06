package org.mmsy.merchant.service;

import org.dromara.common.core.service.MerchantService;
import org.mmsy.merchant.domain.vo.MerMerchantVo;
import org.mmsy.merchant.domain.bo.MerMerchantBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员/租户Service接口
 *
 * @author luohan
 * @date 2025-11-05
 */
public interface IMerMerchantService extends MerchantService {

    /**
     * 根据租户ID查询会员信息
     *
     * @param tenantId 租户编号
     * @return 会员/租户
     */
    @Override
    MerMerchantVo queryByTenantId(String tenantId);

    /**
     * 查询会员/租户
     *
     * @param id 主键
     * @return 会员/租户
     */
    MerMerchantVo queryById(Long id);

    /**
     * 分页查询会员/租户列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员/租户分页列表
     */
    TableDataInfo<MerMerchantVo> queryPageList(MerMerchantBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会"租户列表
     *
     * @param bo 查询条件
     * @return 会员/租户列表
     */
    List<MerMerchantVo> queryList(MerMerchantBo bo);

    /**
     * 新增会员/租户
     *
     * @param bo 会员/租户
     * @return 是否新增成功
     */
    Boolean insertByBo(MerMerchantBo bo);
    /**
     * 新增会员/租户并返回ID
     *
     * @param bo 会员/租户
     * @return 新增的会员ID
     */
    Long insertByBoAndReturnId(MerMerchantBo bo);

    /**
     * 修改会员/租户
     *
     * @param bo 会员/租户
     * @return 是否修改成功
     */
    Boolean updateByBo(MerMerchantBo bo);

    /**
     * 校验并批量删除会"租户信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校"
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改会员/租户状"
     *
     * @param id     主键
     * @param status 状态：1 正常" 禁用
     * @return 是否修改成功
     */
    Boolean updateStatus(Long id, Long status);

    // 以下方法继承自 MerchantService 接口：
    // - MerMerchantVo queryByTenantId(String tenantId)
    // - List<String> queryAuthorizedAppCodes(Long merchantId)
    // - List<String> queryAuthorizedAiAgentCodes(Long merchantId)
}

